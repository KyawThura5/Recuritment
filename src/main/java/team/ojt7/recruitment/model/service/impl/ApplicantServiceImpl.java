package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.ApplicantCodeGenerator;


@Service
public class ApplicantServiceImpl implements ApplicantService{
	
	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private ApplicantCodeGenerator applicantCodeGenerator;
	
	@Autowired
	private ServerFileStorgeService fileStorgeService;
	
	@Autowired
	private String applicantCvLocation;
	
	@Autowired
	private HttpSession session;

	@Override
	@Transactional
	public ApplicantDto save(Applicant applicant, CommonsMultipartFile cvFile) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Applicant duplicatedEntry = applicantRepo.findByCodeAndIsDeleted(applicant.getCode(), false);
		
		if (duplicatedEntry != null && !Objects.equals(applicant.getId(), duplicatedEntry.getId()) ) {
			invalidFieldsException.addField(new InvalidField("code", "duplicated", "An applicant with this code already exists"));
		}
		
		if (applicant.getId() == null && (cvFile == null || cvFile.isEmpty())) {
			invalidFieldsException.addField(new InvalidField("attachedUri", "empty", "Select a cv file"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		if (applicant.getCreatedUser() == null) {
			applicant.setCreatedUser((User) session.getAttribute("loginUser"));
		}
		
		Applicant savedApplicant = applicantRepo.save(applicant);
		if (cvFile != null && !cvFile.isEmpty()) {
			if (StringUtils.hasLength(savedApplicant.getAttachedUri())) {
				fileStorgeService.delete(savedApplicant.getAttachedUri());
			}
			String savedFilePath = fileStorgeService.store(applicantCvLocation, savedApplicant.getCode(), cvFile);
			savedApplicant.setAttachedUri(savedFilePath);
			savedApplicant = applicantRepo.save(savedApplicant);
		}
		
		return ApplicantDto.of(savedApplicant);
	}

	@Override
	public boolean deleteById(Long id) {
		applicantRepo.deleteById(id);
		return true;
	}

	@Override
	public Optional<ApplicantDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Applicant applicant = applicantRepo.findById(id).orElse(null);
		return Optional.ofNullable(ApplicantDto.of(applicant));
	}

	@Override
	public ApplicantDto generateNewWithCode() {
		ApplicantDto dto = new ApplicantDto();
		Long maxId = applicantRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		dto.setCode(applicantCodeGenerator.generate(id));
		return dto;
	}


	@Override
	public Page<ApplicantDto> search(ApplicantSearch applicantSearch) {
		String keyword = applicantSearch.getKeyword() == null ? "%%" : "%" + applicantSearch.getKeyword() + "%";
		LocalDateTime dateFrom = applicantSearch.getDateFrom() == null ? null : LocalDateTime.of(applicantSearch.getDateFrom(), LocalTime.of(0, 0));
		LocalDateTime dateTo = applicantSearch.getDateTo() == null ? null : LocalDateTime.of(applicantSearch.getDateTo(), LocalTime.of(23, 59));
		Page<Applicant> applicants =  applicantRepo.search(keyword, dateFrom, dateTo, PageRequest.of(applicantSearch.getPage() - 1, applicantSearch.getSize())); 
		Pageable applicantsPageable = applicants.getPageable();
		Page<ApplicantDto> page = new PageImpl<ApplicantDto>(ApplicantDto.ofList(applicants.getContent()), applicantsPageable, applicants.getTotalElements());
		
		return page;
	}

}

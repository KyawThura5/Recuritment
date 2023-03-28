package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
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
		
		if (applicant.getId() == null) {
			applicant.setUpdatedOn(LocalDateTime.now());
		} else {
			applicant.setUpdatedOn(findById(applicant.getId()).get().getUpdatedOn());
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
	public List<ApplicantDto> findAll() {
		User loginUser = (User) session.getAttribute("loginUser");
		List<Applicant> applicants = applicantRepo.findAll();
		List<ApplicantDto> dtoList = ApplicantDto.ofList(applicants);
		dtoList.forEach(a -> a.setUpdatableStatus(
				(loginUser.getRole() == Role.GENERAL_MANAGER ||
				loginUser.getRole() == Role.HIRING_MANAGER ||
				a.getStatus().getStep() > 2)
				&& !a.getVacancy().isDeleted()
				));
		return dtoList;
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
		
		User loginUser = (User) session.getAttribute("loginUser");
		Applicant applicant = applicantRepo.findById(id).orElse(null);
		ApplicantDto dto = ApplicantDto.of(applicant);
		dto.setUpdatableStatus(
				(loginUser.getRole() == Role.GENERAL_MANAGER ||
				loginUser.getRole() == Role.HIRING_MANAGER ||
				dto.getStatus().getStep() > 2)
				&& !dto.getVacancy().isDeleted()
				);
		return Optional.ofNullable(dto);
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
		User loginUser = (User) session.getAttribute("loginUser");
		String keyword = applicantSearch.getKeyword() == null ? "%%" : "%" + applicantSearch.getKeyword() + "%";
		Status status = applicantSearch.getStatus();
		LocalDateTime dateFrom = applicantSearch.getDateFrom() == null ? null : LocalDateTime.of(applicantSearch.getDateFrom(), LocalTime.of(0, 0));
		LocalDateTime dateTo = applicantSearch.getDateTo() == null ? null : LocalDateTime.of(applicantSearch.getDateTo(), LocalTime.of(23, 59));
		Page<Applicant> applicants =  applicantRepo.search(
				keyword,
				status,
				dateFrom,
				dateTo,
				PageRequest.of(
						applicantSearch.getPage() - 1,
						applicantSearch.getSize(),applicantSearch.getSort().getSort())
				); 
		Pageable applicantsPageable = applicants.getPageable();
		List<ApplicantDto> dtoList = ApplicantDto.ofList(applicants.getContent());
		dtoList.forEach(a -> a.setUpdatableStatus(
				(loginUser.getRole() == Role.GENERAL_MANAGER ||
				loginUser.getRole() == Role.HIRING_MANAGER ||
				a.getStatus().getStep() > 2)
				&& !a.getVacancy().isDeleted()
			));
		Page<ApplicantDto> page = new PageImpl<ApplicantDto>(dtoList, applicantsPageable, applicants.getTotalElements());
		
		return page;
	}

	@Override
	public List<ApplicantDto> getAllAvailableForNewInterview() {
		return findAll().stream().filter(a -> 
			a.isAvailableForNewInterview()
		).toList();
	}

	@Override
	public void saveJoinDate(Long applicantId, LocalDate joinDate) {
		Applicant applicant = applicantRepo.findById(applicantId).get();
		applicant.setJoinDate(joinDate);
		applicantRepo.save(applicant);
	}

	@Override
	public List<ApplicantDto> findByHiredDateRange(LocalDate dateFrom, LocalDate dateTo) {
		LocalDateTime dateTimeFrom = dateFrom == null ? null : dateFrom.atStartOfDay();
		LocalDateTime dateTimeTo = dateTo == null ? null : dateTo.plusDays(1).atStartOfDay();
		return ApplicantDto.ofList(applicantRepo.findByHiredDateRange(dateTimeFrom, dateTimeTo));
	}

	@Override
	public List<ApplicantDto> findByCreatedDateRange(LocalDate dateFrom, LocalDate dateTo) {
		LocalDateTime dateTimeFrom = dateFrom == null ? null : dateFrom.atStartOfDay();
		LocalDateTime dateTimeTo = dateTo == null ? null : dateTo.plusDays(1).atStartOfDay();
		return ApplicantDto.ofList(applicantRepo.findByCreatedDateRange(dateTimeFrom, dateTimeTo));
	}

}

package team.ojt7.recruitment.model.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.util.generator.ApplicantCodeGenerator;


@Service
public class ApplicantServiceImp implements ApplicantService{
	
	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private ApplicantCodeGenerator applicantCodeGenerator;

	@Override
	public ApplicantDto save(Applicant applicant) {
		// TODO Auto-generated method stub
		return null;
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
		
		Page<Applicant> applicants =  applicantRepo.search(keyword, applicantSearch.getDateFrom(), applicantSearch.getDateTo(), PageRequest.of(applicantSearch.getPage() - 1, applicantSearch.getSize())); 
		Pageable applicantsPageable = applicants.getPageable();
		Page<ApplicantDto> page = new PageImpl<ApplicantDto>(ApplicantDto.ofList(applicants.getContent()), applicantsPageable, applicants.getTotalElements());
		
		return page;
	}

}

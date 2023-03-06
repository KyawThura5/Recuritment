package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.entity.Applicant;

public interface ApplicantService {

	ApplicantDto save(Applicant applicant, CommonsMultipartFile cvFile);
	
	boolean deleteById(Long id);
	
	Optional<ApplicantDto> findById(Long id);
	List<ApplicantDto> findAll();
	
	ApplicantDto generateNewWithCode();
	
	Page<ApplicantDto> search(ApplicantSearch applicantSearch);
	
	
	

}

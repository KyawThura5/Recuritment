package team.ojt7.recruitment.model.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.entity.Applicant;

public interface ApplicantService {

	ApplicantDto save(Applicant applicant);
	
	boolean deleteById(Long id);
	
	Optional<ApplicantDto> findById(Long id);
	
	ApplicantDto generateNewWithCode();
	
	Page<ApplicantDto> search(ApplicantSearch applicantSearch);

}

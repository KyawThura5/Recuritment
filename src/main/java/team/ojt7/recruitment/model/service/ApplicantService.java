package team.ojt7.recruitment.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.entity.Applicant;

public interface ApplicantService {
ApplicantDto save(Applicant applicant);
	
	boolean deleteById(Long id);
	
	Optional<ApplicantDto> findById(Long id);
	
	ApplicantDto generateNewWithCode();
	
	List<ApplicantDto> search(String keyword,LocalDateTime dateFrom, LocalDateTime dateTo);
}

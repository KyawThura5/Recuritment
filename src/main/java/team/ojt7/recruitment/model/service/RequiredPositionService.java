package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;

public interface RequiredPositionService {
	List<RequirePositionDto> findAllByApplicant(ApplicantDto applicant);
	
	Optional<RequirePositionDto> findById(Long id);
}

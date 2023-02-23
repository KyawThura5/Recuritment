package team.ojt7.recruitment.model.service;

import java.util.List;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;

public interface RequiredPositionService {
	List<RequirePositionDto> findAllByApplicant(ApplicantDto applicant);
}

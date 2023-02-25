package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.repo.RequirePositionRepo;
import team.ojt7.recruitment.model.service.RequiredPositionService;



@Service
public class RequiredPositionServiceImpl implements RequiredPositionService{
	@Autowired
	private RequirePositionRepo repo;

	public List<RequirePositionDto> findAll() {		
		return RequirePositionDto.ofList(repo.findAll());
	}


	@Override
	public List<RequirePositionDto> findAllByApplicant(ApplicantDto applicant) {
		List<RequirePositionDto> requireposition = new ArrayList<>(findAll());
		if (applicant.getRequirePosition() != null) {
			if (applicant.getRequirePosition() != null && !requireposition.contains(applicant.getRequirePosition())) {
				requireposition.add(applicant.getRequirePosition());
			}
		}
		return requireposition;
	}


	@Override
	public Optional<RequirePositionDto> findById(Long id) {
		RequirePositionDto dto = RequirePositionDto.of(repo.findById(id).orElse(null));
		return Optional.ofNullable(dto);
	}

	
	
}

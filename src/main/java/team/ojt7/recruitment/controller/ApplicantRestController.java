package team.ojt7.recruitment.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team.ojt7.recruitment.model.dto.ApplicantRequirePositionDto;
import team.ojt7.recruitment.model.dto.PositionSearchByVacancy;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.service.VacancyService;

@RestController
public class ApplicantRestController {
@Autowired
VacancyService service;
@PostMapping("/api/positionByVacancy/search")
public List<? extends ApplicantRequirePositionDto> findPosition(@RequestBody PositionSearchByVacancy dto) {
	VacancyDto vacancyDto = service.findById(dto.getVacancyId()).orElse(null);
	List<RequirePositionDto> result = vacancyDto == null ? Collections.emptyList() :vacancyDto.getRequirePositions();
	List<ApplicantRequirePositionDto> finalResult = new ArrayList<>(result.size());
	for (RequirePositionDto rDto : result) {
		ApplicantRequirePositionDto ardto = new ApplicantRequirePositionDto();
		ardto.setId(rDto.getId());
		ardto.setFoc(rDto.isFoc());
		ardto.setPosition(rDto.getPosition());
		ardto.setTeam(rDto.getTeam());
		finalResult.add(ardto);
	}
	return finalResult;
} 
}

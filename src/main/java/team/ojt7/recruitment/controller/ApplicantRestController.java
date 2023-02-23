package team.ojt7.recruitment.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.ojt7.recruitment.model.dto.PositionSearchByVacancy;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.service.VacancyService;

@RestController
public class ApplicantRestController {
@Autowired
VacancyService service;
@PostMapping("/user/api/positionByVacancy/search")
public ResponseEntity<List<? extends RequirePositionDto>> findPosition(@RequestBody PositionSearchByVacancy dto) {
	VacancyDto vacancyDto = service.findById(dto.getVacancyId()).orElse(null);
	return ResponseEntity.ok(vacancyDto == null ? Collections.emptyList() :vacancyDto.getRequirePositions());
} 
}

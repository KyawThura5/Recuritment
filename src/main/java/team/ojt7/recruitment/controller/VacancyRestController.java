package team.ojt7.recruitment.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.TeamSearchByDepartmentIdDto;
import team.ojt7.recruitment.model.service.DepartmentService;

@RestController
public class VacancyRestController {
	
	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/user/api/teamByDepartmentId/search")
	public ResponseEntity<List<? extends TeamDto>> findTeam(@RequestBody TeamSearchByDepartmentIdDto dto) {
		DepartmentDto departmentDto = departmentService.findById(dto.getDepartmentId()).orElse(null);
		return ResponseEntity.ok(departmentDto == null ? Collections.emptyList() : departmentDto.getTeams());
	}
}

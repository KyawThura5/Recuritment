package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.TeamSearchByDepartmentIdDto;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.TeamService;

@RestController
public class AjaxRestController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/api/team/search")
	public ResponseEntity<List<? extends TeamDto>> findTeam(@RequestBody TeamSearchByDepartmentIdDto dto) {
		List<TeamDto> teams = departmentService.findById(dto.getDepartmentId()).get().getTeams();
		return ResponseEntity.ok(teams);
	}
}

package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.TeamService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class TeamController {
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/admin/team/search")
	public String searchTeams(
			@RequestParam(required = false) String keyword,
			ModelMap model
			) {
		List<TeamDto> teamDtos = teamService.search(keyword);
		model.put("teamList", teamDtos);
		
		return "teams";
	}

	@GetMapping("/admin/team/edit")
	public String editTeam(@RequestParam(required = false) Long id, ModelMap model) {
		List<DepartmentDto> departmentDtos = departmentService.findAll();
		TeamDto teamDto = teamService.findById(id).orElse(new TeamDto());
		model.put("team", teamDto);
		model.put("departmentList", departmentDtos);
		return "edit-team";
	}

	@PostMapping("/admin/team/save")
	public String saveTeam(@ModelAttribute("team")@Validated TeamDto dto, BindingResult bs, ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				teamService.save(TeamDto.parse(dto));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidFiled : e.getFields()) {
					bs.rejectValue(invalidFiled.getField(), invalidFiled.getCode(), invalidFiled.getMessage());
				}
			}
		}
		
		if (bs.hasErrors()) {
			List<DepartmentDto> departmentDtos = departmentService.findAll();
			model.put("departmentList", departmentDtos);
			return "edit-team";
		}
		
		return "redirect:/admin/team/search";
	}

	@PostMapping("/admin/team/delete")
	public String deleteTeam(@RequestParam("id") Long id) {

		teamService.deleteById(id);
		return "redirect:/admin/team/search";
	}

}

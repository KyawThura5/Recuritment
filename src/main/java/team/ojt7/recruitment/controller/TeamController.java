package team.ojt7.recruitment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.Formatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.TeamSearch;
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
	
	@Autowired
	private Formatter<DepartmentDto> departmentDtoFormatter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(departmentDtoFormatter);
	}
	
	@GetMapping("/team/search")
	public String searchTeams(
			@ModelAttribute("teamSearch")
			TeamSearch teamSearch,
			ModelMap model
			) {
		
		Page<TeamDto>teamPage=teamService.findpage(teamSearch);
		
		model.put("teamPage", teamPage);
		model.put("departmentList", departmentService.findAll());
		return "teams";
	}

	@GetMapping("/team/edit")
	public String editTeam(@RequestParam(required = false) Long id, ModelMap model) {
		
		TeamDto teamDto = teamService.findById(id).orElse(new TeamDto());
		List<DepartmentDto> departmentDtos = departmentService.findAllForTeam(teamDto);
		model.put("team", teamDto);
		model.put("departmentList", departmentDtos);
		String title = teamDto.getId() == null ? "Add New Team" : "Edit Team";
		model.put("title", title);
		return "edit-team";
	}

	@PostMapping("/team/save")
	public String saveTeam(
			@ModelAttribute("team")
			@Validated
			TeamDto dto,
			BindingResult bs,
			RedirectAttributes redirect,
			ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				teamService.save(TeamDto.parse(dto));
				String message = dto.getId() == null ? "Successfully created a new team." : "Successfully updated the team.";
				String cssClass = dto.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidFiled : e.getFields()) {
					bs.rejectValue(invalidFiled.getField(), invalidFiled.getCode(), invalidFiled.getMessage());
				}
			}
		}
		
		if (bs.hasErrors()) {
			List<DepartmentDto> departmentDtos = departmentService.findAll();
			model.put("departmentList", departmentDtos);
			String title = dto.getId() == null ? "Add New Team" : "Edit Team";
			model.put("title", title);
			return "edit-team";
		}
		
		return "redirect:/team/search";
	}
	
	@GetMapping("/team/data")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDataFormTeamEdit(
			@RequestParam(required = false)
			Long id
			) {
		Map<String, Object> data = new HashMap<>();
		TeamDto teamDto = teamService.findById(id).orElse(new TeamDto());
		List<DepartmentDto> departmentDtos = departmentService.findAllForTeam(teamDto);
		data.put("team", teamDto);
		data.put("departmentList", departmentDtos);
		String title = teamDto.getId() == null ? "Add New Team" : "Edit Team";
		data.put("title", title);
		return ResponseEntity.ok(data);
	}
	
	@PostMapping("/team/validate")
	@ResponseBody
	public ResponseEntity<Map<String, String>> validateTeam(
			@Validated
			@ModelAttribute("team")
			TeamDto team,
			BindingResult bs
			) {
		Map<String, String> validation = new HashMap<>();
		
		try {
			teamService.checkValidation(team);
		} catch (InvalidFieldsException e) {
			for (InvalidField invalidField : e.getFields()) {
				bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
			}
		}
		
		if (bs.hasErrors()) {
			for (FieldError error : bs.getFieldErrors()) {
				validation.put(error.getField(), error.getDefaultMessage());
			}
		}
		
		return ResponseEntity.ok(validation);
	}

	@PostMapping("/team/delete")
	public String deleteTeam(
			@RequestParam("id")
			Long id,
			RedirectAttributes redirect) {

		teamService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the team.", "notice-success"));
		return "redirect:/team/search";
	}

}

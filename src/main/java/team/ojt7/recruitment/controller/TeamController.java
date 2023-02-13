package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.TeamService;

@Controller
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private DepartmentService departmentService;
	@RequestMapping(value = "/admin/team/add", method = RequestMethod.GET)
	public String addNewTeam(@RequestParam(required = false) String keyword,ModelMap model) {
		List<DepartmentDto> list = departmentService.search(keyword);
		model.addAttribute("list", list);
		model.addAttribute("team",new TeamDto());		
		return "addteam";
	}
	@GetMapping("/admin/team/search")
	public String searchTeams(@RequestParam(required = false) String keyword, ModelMap model) {
		List<TeamDto> list = teamService.search(keyword);
		model.addAttribute("list", list);
		return "teams";
	}
	@GetMapping("/admin/team/edit")
	public String editTeam(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		TeamDto teamDto = teamService.findById(id).orElse(new TeamDto());
		model.put("team", teamDto);
		return "addteam";
	}

	@PostMapping("/admin/team/save")
	public String saveTeam(@ModelAttribute("team") TeamDto dto,BindingResult bs,ModelMap model) {
		if(bs.hasErrors()) {
			return "addteam";
		}
		
		teamService.save(TeamDto.parse(dto));
		return "redirect:/admin/team/search";
	}

	@GetMapping("/admin/team/delete")
	public String deleteTeam(@RequestParam("id") Long id) {
		
		teamService.deleteById(id);
		return "redirect:/admin/team/search";
	}

}

package team.ojt7.recruitment.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.PositionService;

@Controller
public class VacancyTestController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private Formatter<DepartmentDto> departmentDtoFormatter;
	
	@Autowired
	private Formatter<TeamDto> teamDtoFormatter;
	
	@Autowired
	private Formatter<PositionDto> positionDtoFormatter;
	
	@Autowired
	private Formatter<UserDto> userDtoFormatter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(departmentDtoFormatter);
		binder.addCustomFormatter(teamDtoFormatter);
		binder.addCustomFormatter(positionDtoFormatter);
		binder.addCustomFormatter(userDtoFormatter);
	}

	@GetMapping("/dh/test/vacancy/edit")
	public String editVacancy(
			@RequestParam(required = false)
			Long id,
			ModelMap model) {
		List<DepartmentDto> departments = departmentService.findAll();
		List<PositionDto> positions = positionService.findAll();
		
		model.put("departments", departments);
		model.put("positions", positions);
		model.put("vacancy", id == null ? new VacancyDto() : VacancyDto.of(vacancyRepo.findById(id).get()));
		return "edit-vacancy";
	}
	
	@PostMapping("/dh/test/vacancy/save")
	public String saveVacancy(
			@Validated
			@ModelAttribute("vacancy")
			VacancyDto vacancyDto,
			BindingResult bindingResult,
			ModelMap model,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (bindingResult.hasErrors()) {
			List<DepartmentDto> departments = departmentService.findAll();
			List<PositionDto> positions = positionService.findAll();
			model.put("departments", departments);
			model.put("positions", positions);
			return "edit-vacancy";
		}
		Vacancy vacancy = VacancyDto.parse(vacancyDto);
		if (vacancy.getCreatedUser() == null) {
			vacancy.setCreatedUser(loginUser);
		}
		vacancyRepo.save(vacancy);
		return "redirect:/manager/test/vacancy/search";
	}
	
	@GetMapping("/manager/test/vacancy/search")
	public String searchVacancies(ModelMap model) {
		model.put("vacancies", vacancyRepo.findAll());
		return "vacancies";
	}
}

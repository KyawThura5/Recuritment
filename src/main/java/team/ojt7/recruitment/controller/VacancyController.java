package team.ojt7.recruitment.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.PositionService;
import team.ojt7.recruitment.model.service.VacancyService;
import team.ojt7.recruitment.model.validator.VacancyValidator;

@Controller
public class VacancyController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private VacancyService vacancyTestService;
	
	@Autowired
	private Formatter<DepartmentDto> departmentDtoFormatter;
	
	@Autowired
	private Formatter<TeamDto> teamDtoFormatter;
	
	@Autowired
	private Formatter<PositionDto> positionDtoFormatter;
	
	@Autowired
	private Formatter<UserDto> userDtoFormatter;
	
	@Autowired
	private VacancyValidator vacancyValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(departmentDtoFormatter);
		binder.addCustomFormatter(teamDtoFormatter);
		binder.addCustomFormatter(positionDtoFormatter);
		binder.addCustomFormatter(userDtoFormatter);
		
		if (binder.getTarget() != null && vacancyValidator.supports(binder.getTarget().getClass())) {
			binder.addValidators(vacancyValidator);
		}
	}

	@GetMapping("/vacancy/edit")
	public String editVacancy(
			@RequestParam(required = false)
			Long id,
			ModelMap model) {
		VacancyDto vacancyDto = vacancyTestService.findById(id).orElse(vacancyTestService.generateNewWihCode());
		List<DepartmentDto> departments = departmentService.findAllForVacancy(vacancyDto);
		List<PositionDto> positions = positionService.findAllForVacancy(vacancyDto);
		
		if (vacancyDto.getId() != null) {
			
		}
		
		model.put("departments", departments);
		model.put("positions", positions);
		model.put("vacancy", vacancyDto);
		return "edit-vacancy";
	}
	
	@PostMapping("/vacancy/save")
	public String saveVacancy(
			@Validated
			@ModelAttribute("vacancy")
			VacancyDto vacancyDto,
			BindingResult bindingResult,
			ModelMap model,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (bindingResult.hasErrors()) {
			List<DepartmentDto> departments = departmentService.findAllForVacancy(vacancyDto);
			List<PositionDto> positions = positionService.findAllForVacancy(vacancyDto);
			model.put("departments", departments);
			model.put("positions", positions);
			return "edit-vacancy";
		}
		Vacancy vacancy = VacancyDto.parse(vacancyDto);
		if (vacancy.getCreatedUser() == null) {
			vacancy.setCreatedUser(loginUser);
		}
		vacancyTestService.save(vacancy);
		return "redirect:/vacancy/search";
	}
	
	@GetMapping("/vacancy/search")
	public String searchVacancies(
			@ModelAttribute("vacancySearch")
			VacancySearch vacancySearch,
			ModelMap model) {
		Page<VacancyDto> vacancyPage = vacancyTestService.search(vacancySearch);
		model.put("vacancySearch", vacancySearch);
		model.put("vacancyPage", vacancyPage);
		return "vacancies";
	}
	
	@PostMapping("/vacancy/delete")
	public String deleteVacancyById(@RequestParam Long id) {
		vacancyTestService.deleteById(id);
		return "redirect:/vacancy/search";
	}
}

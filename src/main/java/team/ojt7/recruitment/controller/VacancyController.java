package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.service.VacancyService;

@Controller
public class VacancyController {

	@Autowired
	private VacancyService vacancyService;
	
	@GetMapping("/manager/vacancy/search")
	public String searchVacancies(String keyword) {
		return "vacancies";
	}
	
	@GetMapping("/dh/vacancy/edit")
	public String editVacancy(
			@RequestParam(required = false)
			Long id
			) {
		return "edit-vacancy";
	}
	
	@PostMapping("/dh/vacancy/save")
	public String saveVacancy() {
		return "redirect:/manager/vacancy/search";
	}
	
	@PostMapping("/dh/vacancy/delete")
	public String deleteVacancy(
			@RequestParam
			Long id
			) {
		return "redirect:/manager/vacancy/search";
	}
	
	@GetMapping("/manager/vacancy/detail")
	public String showVacancyDetail(
			@RequestParam
			Long id
			) {
		return "vacancy-detail";
	}
}

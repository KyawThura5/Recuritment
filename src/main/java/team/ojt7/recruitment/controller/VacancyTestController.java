package team.ojt7.recruitment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.PositionService;
import team.ojt7.recruitment.model.service.TeamService;

@Controller
public class VacancyTestController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private TeamService teamService;

	@GetMapping("/dh/test/vacancy/edit")
	public String editVacancy(ModelMap model) {
		List<DepartmentDto> departments = departmentService.findAll();
		List<PositionDto> positions = positionService.findAll();
		
		RequirePositionDto rp1 = new RequirePositionDto();
		rp1.setTeam(teamService.findById(3L).get());
		rp1.setFoc(false);
		rp1.setCount(1);
		rp1.setPosition(positionService.findById(1L).get());
		
		RequirePositionDto rp2 = new RequirePositionDto();
		rp2.setTeam(teamService.findById(6L).get());
		rp2.setFoc(true);
		rp2.setCount(2);
		rp2.setPosition(positionService.findById(2L).get());
		
		RequirePositionDto rp3 = new RequirePositionDto();
		rp3.setTeam(teamService.findById(7L).get());
		rp3.setFoc(false);
		rp3.setCount(3);
		rp3.setPosition(positionService.findById(3L).get());
		
		model.put("departments", departments);
		model.put("positions", positions);
		
		VacancyDto vacancyDto = new VacancyDto();
		vacancyDto.setCode("DAT_VACANCY_001");
		vacancyDto.setDueDate(LocalDate.of(2023, 2, 1));
		vacancyDto.setComment("We are hiring");
		vacancyDto.setStatus(Status.CLOSED);
		vacancyDto.setDepartment(departmentService.findById(2L).get());
		vacancyDto.setRequirePositions(List.of(rp1, rp2, rp3));
		model.put("vacancy", vacancyDto);
		return "edit-vacancy";
	}
	
	@PostMapping("/dh/test/vacancy/save")
	public String saveVacancy(@ModelAttribute VacancyDto vacancy) {
		return "redirect:/manager/vacancy/search";
	}
}

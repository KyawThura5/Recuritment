package team.ojt7.recruitment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.RequirePosition;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.repo.VacancyRepo;
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
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private Formatter<DepartmentDto> departmentDtoFormatter;
	
	@Autowired
	private Formatter<TeamDto> teamDtoFormatter;
	
	@Autowired
	private Formatter<PositionDto> positionDtoFormatter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(departmentDtoFormatter);
		binder.addCustomFormatter(teamDtoFormatter);
		binder.addCustomFormatter(positionDtoFormatter);
	}

	@GetMapping("/dh/test/vacancy/edit")
	public String editVacancy(
			@RequestParam(required = false)
			Long id,
			ModelMap model) {
		List<DepartmentDto> departments = departmentService.findAll();
		List<PositionDto> positions = positionService.findAll();
		
//		RequirePositionDto rp1 = new RequirePositionDto();
//		rp1.setTeam(teamService.findById(3L).get());
//		rp1.setFoc(false);
//		rp1.setCount(1);
//		rp1.setPosition(positionService.findById(1L).get());
//		
//		RequirePositionDto rp2 = new RequirePositionDto();
//		rp2.setTeam(teamService.findById(6L).get());
//		rp2.setFoc(true);
//		rp2.setCount(2);
//		rp2.setPosition(positionService.findById(2L).get());
//		
//		RequirePositionDto rp3 = new RequirePositionDto();
//		rp3.setTeam(teamService.findById(7L).get());
//		rp3.setFoc(false);
//		rp3.setCount(3);
//		rp3.setPosition(positionService.findById(3L).get());
		
		model.put("departments", departments);
		model.put("positions", positions);
		
//		VacancyDto vacancyDto = new VacancyDto();
//		vacancyDto.setCode("DAT_VACANCY_001");
//		vacancyDto.setDueDate(LocalDate.of(2023, 2, 1));
//		vacancyDto.setComment("We are hiring");
//		vacancyDto.setStatus(Status.CLOSED);
//		vacancyDto.setDepartment(departmentService.findById(2L).get());
//		vacancyDto.setRequirePositions(List.of(rp1, rp2, rp3));
		model.put("vacancy", id == null ? new VacancyDto() : ofVacancy(vacancyRepo.findById(id).get()));
		return "edit-vacancy";
	}
	
	@PostMapping("/dh/test/vacancy/save")
	public String saveVacancy(@ModelAttribute("vacancy") VacancyDto vacancyDto) {
		Vacancy vacancy = new Vacancy();
		vacancy.setId(vacancyDto.getId());
		vacancy.setCode(vacancyDto.getCode());
		vacancy.setCreatedDate(LocalDate.now());
		vacancy.setDepartment(DepartmentDto.parse(vacancyDto.getDepartment()));
		vacancy.setComment(vacancyDto.getComment());
		vacancy.setDueDate(vacancyDto.getDueDate());
		vacancy.setStatus(vacancyDto.getStatus());
		vacancy.setRequirePositions(vacancyDto.getRequirePositions().stream().map(p -> parseRequirePositionDto(p)).toList());
		vacancyRepo.save(vacancy);
		return "redirect:/manager/test/vacancy/search";
	}
	
	@GetMapping("/manager/test/vacancy/search")
	public String searchVacancies(ModelMap model) {
		model.put("vacancies", vacancyRepo.findAll());
		return "vacancies";
	}
	
	private static VacancyDto ofVacancy(Vacancy vacancy) {
		VacancyDto dto = new VacancyDto();
		dto.setId(vacancy.getId());
		dto.setCode(vacancy.getCode());
		dto.setDueDate(vacancy.getDueDate());
		dto.setCreatedDate(vacancy.getCreatedDate());
		dto.setStatus(vacancy.getStatus());
		dto.setComment(vacancy.getComment());
		dto.setDepartment(DepartmentDto.of(vacancy.getDepartment()));
		dto.setRequirePositions(vacancy.getRequirePositions().stream().map(p -> ofRequirePosition(p)).toList());
		return dto;
	}
	
	private static RequirePosition parseRequirePositionDto(RequirePositionDto dto) {
		RequirePosition entity = new RequirePosition();
		entity.setId(dto.getId());
		entity.setCount(dto.getCount());
		entity.setFoc(dto.isFoc());
		entity.setPosition(PositionDto.parse(dto.getPosition()));
		Team team = new Team();
		team.setId(dto.getTeam().getId());
		team.setName(dto.getTeam().getName());
		entity.setTeam(team);
		return entity;
	}
	
	private static RequirePositionDto ofRequirePosition(RequirePosition entity) {
		RequirePositionDto dto = new RequirePositionDto();
		dto.setId(entity.getId());
		dto.setCount(entity.getCount());
		dto.setFoc(entity.isFoc());
		dto.setPosition(PositionDto.of(entity.getPosition()));
		TeamDto team = new TeamDto();
		team.setId(entity.getTeam().getId());
		team.setName(entity.getTeam().getName());
		dto.setTeam(team);
		return dto;
	}
}

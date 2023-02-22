package team.ojt7.recruitment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.RequirePosition;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.service.VacancyTestService;
import team.ojt7.recruitment.model.service.impl.VacancyTestServiceImpl;
@SpringBootTest
@AutoConfigureMockMvc
class VacancyTestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	VacancyTestServiceImpl vacancyTestService;
	@MockBean
	VacancySearch vacancySearch;

	@Test
	@WithMockUser(authorities = "MANAGER")
	void testInitBinder() throws Exception {
		VacancySearch vsearch=new VacancySearch();
		
	
	}
   
	@Test
	@WithMockUser(authorities = "DEPARTMENT_HEAD")
	void testEditVacancy() throws Exception{
		VacancyDto vacancyDto=new VacancyDto();
		Vacancy vacancy=new Vacancy();
		vacancy.setId(1L);
		vacancy.setCode("DAT_VACANCY_001");
		vacancy.setCreatedDate(LocalDate.now());
		vacancy.setDueDate(LocalDate.now());
		vacancy.setStatus(Status.OPENING);
		vacancy.setComment("Applying for junior");
		vacancy.setDeleted(false);
		vacancyDto.setId(vacancy.getId());
		vacancyDto.setCode(vacancy.getCode());
		vacancyDto.setCreatedDate(vacancy.getCreatedDate());
		vacancyDto.setDueDate(vacancy.getDueDate());
		vacancyDto.setStatus(vacancy.getStatus());
		vacancyDto.setDeleted(false);
		//Long id=vacancyDto.getId();
		when(vacancyTestService.findById(1L)).thenReturn(Optional.of(vacancyDto));
		this.mockMvc.perform(get("/dh/test/vacancy/edit").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-vacancy"));
		
	}
    @Disabled
	@Test
	@WithMockUser(authorities = "DEPARTMENT_HEAD,MANAGER")
	void testSaveVacancy() throws Exception {
//		List<RequirePosition> positions=new ArrayList<>();
//		RequirePosition requirePosition= new RequirePosition();
//		Position position=new Position();
//		position.setId(1L);
//		position.setName("Junior Developer");
////		position.setId(2L);
////		position.setName("Software Engineer");
//		
//		Team team = new Team();
//		team.setId(1L);
//		team.setName("ABC");
//		
		Department department = new Department();
		department.setId(1L);
		department.setName("Dep1");
//		DepartmentDto departmentDto = new DepartmentDto();
//		departmentDto.setId(1L);
//		departmentDto.setName("Dep1");
//		
//		User createdUser = new User();
//		createdUser.setId(1L);
//		createdUser.setName("SuSu");
//		
//		requirePosition.setId(1L);
//		requirePosition.setPosition(position);
//		requirePosition.setTeam(team);
//		requirePosition.setCount(2);
//		
//		positions.add(requirePosition);
		
		LocalDate dateTime=LocalDate.now();
		LocalDate dueDate=LocalDate.of(2023, 04, 22);
		
		VacancyDto vacancy = new VacancyDto();
		vacancy.setId(1L);
		vacancy.setCode("1");
		vacancy.setStatus(Status.OPENING);
		//vacancy.setDepartment(department);
		vacancy.setCreatedDate(dateTime);
		vacancy.setDueDate(dueDate);
		vacancy.setDeleted(false);
				
		 when(vacancyTestService.save(VacancyDto.parse(vacancy))).thenReturn(vacancy);
			this.mockMvc.perform(post("/dh/test/vacancy/save").flashAttr("vacancySearch",VacancyDto.parse(vacancy)))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/manager/test/vacancy/search"));
		
	}
   @Disabled
	@Test
	@WithMockUser(authorities = "MANAGER")
	void testSearchVacancies() throws Exception {
		List<RequirePosition> positions=new ArrayList<>();
		RequirePosition requirePosition= new RequirePosition();
		Position position=new Position();
		position.setId(1L);
		position.setName("Junior Developer");
//		position.setId(2L);
//		position.setName("Software Engineer");
		
		Team team = new Team();
		team.setId(1L);
		team.setName("ABC");
		
		Department department = new Department();
		department.setId(1L);
		department.setName("Dep1");
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(1L);
		departmentDto.setName("Dep1");
		
		User createdUser = new User();
		createdUser.setId(1L);
		createdUser.setName("SuSu");
		
		requirePosition.setId(1L);
		requirePosition.setPosition(position);
		requirePosition.setTeam(team);
		requirePosition.setCount(2);
		
		positions.add(requirePosition);
		
		LocalDate dateTime=LocalDate.now();
		LocalDate dueDate=LocalDate.of(2023, 04, 22);
		
		Vacancy vacancy = new Vacancy();
		vacancy.setId(1L);
		vacancy.setCode("1");
		vacancy.setStatus(Status.OPENING);
		vacancy.setRequirePositions(positions);
		vacancy.setDepartment(department);
		vacancy.setCreatedUser(createdUser);
		vacancy.setCreatedDate(dateTime);
		vacancy.setDueDate(dueDate);
		vacancy.setDeleted(false);
				
		
		vacancySearch.setKeyword("%1%");
		vacancySearch.setStatus(Status.OPENING);
		vacancySearch.setDateFrom(LocalDate.now());
		vacancySearch.setDateTo(LocalDate.of(2023, 04, 22));
		
		
		
		Page<VacancyDto> vacancies = vacancyTestService.search(vacancySearch); 
		
		//when(vacancyTestService.search(vacancySearch)).thenReturn(departmentDtos);
		this.mockMvc.perform(get("/manager/test/vacancy/search").flashAttr("vacancySearch", vacancies))
		.andExpect(status().isOk())
		.andExpect(view().name("vacancies"));
	
	}

	@Test
	@WithMockUser(authorities = "DEPARTMENT_HEAD")
	void testDeleteVacancyById() throws Exception {
		when(vacancyTestService.deleteById(1L)).thenReturn(true);
		this.mockMvc.perform(post("/dh/test/vacancy/delete").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/manager/test/vacancy/search"));
	}

}

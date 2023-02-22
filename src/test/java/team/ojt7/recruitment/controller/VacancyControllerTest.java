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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.service.VacancyService;
@SpringBootTest
@AutoConfigureMockMvc
class VacancyControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	VacancyService vacancyService;
    @Disabled
	@Test
	@WithMockUser(authorities = "MANAGER")
	void testSearchVacancies() throws Exception {
		List<VacancyDto> vacancyDtos=new ArrayList<>();
		VacancyDto vacancyDto=new VacancyDto();
		vacancyDto.setId(1L);
		vacancyDto.setCode("DAT_VACANCY_001");
		vacancyDto.setCreatedDate(LocalDate.now());
		vacancyDto.setDueDate(LocalDate.now());
		vacancyDto.setStatus(Status.OPENING);
		vacancyDto.setComment("Applying for junior");
		vacancyDto.setDeleted(false);
		vacancyDtos.add(vacancyDto);
		when(vacancyService.search("DAT_VACANCY_001", Status.OPENING, LocalDate.now(), LocalDate.now()))
		.thenReturn(vacancyDtos);
		this.mockMvc.perform(get("/manager/vacancy/search").flashAttr("vacancySearch", vacancyDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("vacancies"));
		
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities = "DepartmentHead")
	void testEditVacancy() throws Exception {
		VacancyDto vacancyDto=new VacancyDto();
		vacancyDto.setId(1L);
		vacancyDto.setCode("DAT_VACANCY_001");
		vacancyDto.setCreatedDate(LocalDate.now());
		vacancyDto.setDueDate(LocalDate.now());
		vacancyDto.setStatus(Status.OPENING);
		vacancyDto.setComment("Applying for junior");
		vacancyDto.setDeleted(false);
		when(vacancyService.findById(1L)).thenReturn(Optional.of(vacancyDto));
		this.mockMvc.perform(get("/dh/vacancy/edit").flashAttr("vacancy", vacancyDto))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-vacancy"));
		
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities = "DepartmentHead")
	void testSaveVacancy() throws Exception {
//		Vacancy vacancy=new Vacancy();
//		vacancy.setId(1L);
//		vacancy.setCode("DAT_VACANCY_001");
//		vacancy.setCreatedDate(LocalDate.now());
//		vacancy.setDueDate(LocalDate.now());
//		vacancy.setStatus(Status.OPENING);
//		vacancy.setComment("Applying for junior");
//		vacancy.setDeleted(false);
		VacancyDto vacancyDto=new VacancyDto();
		vacancyDto.setId(1L);
		vacancyDto.setCode("DAT_VACANCY_001");
		vacancyDto.setCreatedDate(LocalDate.now());
		vacancyDto.setDueDate(LocalDate.now());
		vacancyDto.setStatus(Status.OPENING);
		vacancyDto.setComment("Applying for junior");
		vacancyDto.setDeleted(false);
		Vacancy vacancy=VacancyDto.parse(vacancyDto);
		when(vacancyService.save(vacancy)).thenReturn(vacancyDto);

		this.mockMvc.perform(post("/dh/vacancy/save").flashAttr("vacancy", vacancy))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/manager/vacancy/search"));
		//fail("Not yet implemented");
	}

	@Test
	void testDeleteVacancy() {
		fail("Not yet implemented");
	}

	@Test
	void testShowVacancyDetail() {
		fail("Not yet implemented");
	}

}

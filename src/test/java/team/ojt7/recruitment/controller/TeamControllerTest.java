package team.ojt7.recruitment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.repo.TeamRepo;
import team.ojt7.recruitment.model.service.impl.DepartmentServiceImpl;
import team.ojt7.recruitment.model.service.impl.TeamServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
	@Autowired
	MockMvc mock;
	@MockBean
	TeamServiceImpl service;
	@MockBean
	TeamRepo repo;
	@MockBean
	DepartmentServiceImpl service1;
	@MockBean
	DepartmentRepo repo1;
	@Test
	void testSearchTeams() {
		fail("Not yet implemented");
	}

	@Test
	void testEditTeam() {
		TeamDto dto=new TeamDto();
		Long id=(long) 001;
		dto.setId(id);
		dto.setName("Team A");
		DepartmentDto d=new DepartmentDto();
		d.setName("Java");
		dto.setDepartment(d);
		when(service.findById(id)).thenReturn(Optional.ofNullable(dto));
				
		}

	@Test
	void testSaveTeam() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteTeam() {
		fail("Not yet implemented");
	}

}

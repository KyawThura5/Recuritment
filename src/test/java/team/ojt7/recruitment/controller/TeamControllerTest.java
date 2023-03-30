package team.ojt7.recruitment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.repo.TeamRepo;
import team.ojt7.recruitment.model.service.impl.DepartmentServiceImpl;
import team.ojt7.recruitment.model.service.impl.TeamServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
	@Autowired
	MockMvc mock;
	@Autowired
	TeamServiceImpl service;
	@MockBean
	TeamRepo repo;
	@Autowired
	DepartmentServiceImpl service1;
	@MockBean
	DepartmentRepo repo1;
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSearchTeams() throws Exception {
		String keyword="A";
		List<TeamDto>list=new ArrayList<>();
		TeamDto team=new TeamDto();
		Long id=(long)1;
		team.setId(id);
		team.setName("A");
		DepartmentDto d=new DepartmentDto();
		d.setId(Long.valueOf(1));
		d.setName("Java");
		List<TeamDto>list1=new ArrayList<>();
		d.setTeams(list1);
		team.setDepartment(d);

		TeamDto team1=new TeamDto();
		Long id1=(long)1;
		team1.setId(id1);
		team1.setName("A");
		DepartmentDto d1=new DepartmentDto();
		d1.setId(Long.valueOf(1));
		d1.setName("Java");
		List<TeamDto>list2=new ArrayList<>();
		d1.setTeams(list2);
		team1.setDepartment(d1);
		list.add(team);
		list.add(team1);
		when(service.search("%"+keyword+"%")).thenReturn(list);
		this.mock.perform(get("/admin/team/search"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditTeam() throws Exception{	
		TeamDto dto=new TeamDto();
		dto.setId(Long.valueOf(1));
		dto.setName("Team A");
		dto.setDeleted(false);
		DepartmentDto d=new DepartmentDto();
		d.setId(Long.valueOf(1));
		d.setName("Java");
		d.setDeleted(false);
		List<TeamDto>list=new ArrayList<>();
		d.setTeams(list);
		dto.setDepartment(d);
		
		Team team=new Team();
		team.setId(Long.valueOf(1));
		team.setName("Team A");
		team.setDeleted(false);
		Department d1=new Department();
		d1.setId(Long.valueOf(1));
		d1.setName("Java");
		d1.setDeleted(false);
		List<Team>list1=new ArrayList<>();
		d1.setTeams(list1);
		team.setDepartment(d1);
		this.mock.perform(get("/admin/team/edit").param("id","1"));	
		}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSaveTeam() throws Exception {
		Team dto=new Team();
		dto.setId(Long.valueOf(1));
		dto.setName("Team A");
		Department d=new Department();
		d.setId(Long.valueOf(1));
		d.setName("Java");
		List<Team>list=new ArrayList<>();
		d.setTeams(list);
		dto.setDepartment(d);
		
		TeamDto dto1=new TeamDto();
		dto1.setId(Long.valueOf(1));
		dto1.setName("Team A");
		DepartmentDto d1=new DepartmentDto();
		d1.setId(Long.valueOf(1));
		d1.setName("Java");
		List<TeamDto>list1=new ArrayList<>();
		d1.setTeams(list1);
		dto.setDepartment(d);
		this.mock.perform(post("/admin/team/save").flashAttr("team",dto));
		}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteTeam() throws Exception {
		this.mock.perform(post("/admin/team/delete").param("id","1"));
	}

}

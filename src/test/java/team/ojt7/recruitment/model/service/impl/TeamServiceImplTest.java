package team.ojt7.recruitment.model.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.repo.TeamRepo;

@SpringBootTest
class TeamServiceImplTest {
@Mock
TeamRepo repo;
@InjectMocks
TeamServiceImpl service;
	@Test
	void testSearch() {
		String keyword="Ja";
		Team team=new Team();
		team.setName("Team A");
		Department dep=new Department();
		dep.setName("Java");
		team.setDepartment(dep);
		
		Team team1=new Team();
		team1.setName("Team B");
		Department dep1=new Department();
		dep1.setName("Javascript");
		team1.setDepartment(dep1);
		
		List<Team>list=new ArrayList<>();
		list.add(team);
		list.add(team1);
		when(repo.search("%"+keyword+"%")).thenReturn(list);
		List<TeamDto>list1=service.search(keyword);
		assertEquals(2,list1.size());
		verify(repo).search("%"+keyword+"%");
	}

	@Test
	void testFindById() {
		Team team=new Team();
		Long id=(long) 001;
		team.setId(id);
		team.setName("Team A");
		Department d=new Department();
		d.setName("Java");
		team.setDepartment(d);
		when(repo.findById(team.getId())).thenReturn(Optional.ofNullable(team));
		Optional<TeamDto> t=service.findById(team.getId());
		assertEquals(001, t.get().getId());
		assertEquals("Team A", t.get().getName());
		assertEquals("Java", t.get().getDepartment().getName());
		verify(repo).findById(team.getId());
	}

	@Test
	void testSave() {
		Team team=new Team();
		Long id=(long) 001;
		team.setId(id);
		team.setName("Team A");
		Department d=new Department();
		d.setName("Java");
		team.setDepartment(d);
		service.save(team);
		verify(repo).save(team);
	}

	@Test
	void testDeleteById() {
		Long id=(long) 001;
		service.deleteById(id);
		verify(repo).deleteById(id);
	}

}

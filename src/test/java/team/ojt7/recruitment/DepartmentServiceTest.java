package team.ojt7.recruitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.service.impl.DepartmentServiceImpl;

@SpringBootTest
public class DepartmentServiceTest {
	@InjectMocks
	private DepartmentServiceImpl departmentService;
	@Mock
	private DepartmentRepo departmentRepo;
	@Test
	public void testSearch() {
		List<Department> resultList=new ArrayList<>();
		Department department=new Department();
		department.setId(1L);
		department.setName("Software Department");
		department.setDeleted(false);
		department.setTeams(department.getTeams());
		resultList.add(department);
		when(departmentRepo.search("%Software Department%")).thenReturn(resultList);
		List<DepartmentDto> departmentList=departmentService.search("Software Department");
		assertEquals(1,departmentList.size());
		verify(departmentRepo,times(1)).search("%Software Department%");
		
	}
	@Test
	public void testfindById() {
		Department department=new Department();
		department.setId(1L);
		department.setName("Software Department");
		department.setDeleted(false);
		//department.setTeams(department.getTeams());
		when(departmentRepo.findById(department.getId())).thenReturn(Optional.of(department));
		DepartmentDto getDepartment=departmentService.findById(department.getId()).get();
		assertEquals("Software Department",getDepartment.getName());
		
	}
	@Test
	public void testFindAll() {
		List<Department> resultList=new ArrayList<>();
		Department department=new Department();
		department.setId(1L);
		department.setName("Software Department");
		department.setDeleted(false);
		department.setTeams(department.getTeams());
		resultList.add(department);
		when(departmentRepo.findAll()).thenReturn(resultList);
		List<DepartmentDto> departmentList=departmentService.findAll();
		assertEquals(1,departmentList.size());
		verify(departmentRepo,times(1)).findAll();
		
	}
	@Test
	public void testSave() {
		Department department=new Department();
		department.setId(1L);
		department.setName("Software Department");
		department.setDeleted(false);
		departmentService.save(department);
		//when(departmentRepo.save(department)).thenReturn(department);
		verify(departmentRepo,times(1)).save(department);
		
	}
	@Test
	public void testDeleteById() {
		departmentService.deleteById(1L);
		verify(departmentRepo,times(1)).deleteById(1L);
		
	}

}

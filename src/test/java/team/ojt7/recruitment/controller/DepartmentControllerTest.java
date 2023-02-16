package team.ojt7.recruitment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.service.DepartmentService;
@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	DepartmentService departmentService;

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSearchDepartments() throws Exception {
		List<DepartmentDto> departmentDtos=new ArrayList<>();
		DepartmentDto departmentDto=new DepartmentDto();
		departmentDto.setId(1L);
		departmentDto.setName("Software Engineer");
		departmentDtos.add(departmentDto);
		when(departmentService.search(departmentDto.getName())).thenReturn(departmentDtos);
		this.mockMvc.perform(get("/admin/department/search").flashAttr("department", departmentDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("departments"));
		
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities="ADMIN")
	void testEditDepartment() throws Exception {
		DepartmentDto departmentDto=new DepartmentDto();
		when(departmentService.findById(departmentDto.getId())).thenReturn(Optional.of(departmentDto));
		this.mockMvc.perform(get("/admin/department/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-department"));
		
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities="ADMIN")
	void testSaveDepartment() throws Exception{
		DepartmentDto departmentDto=new DepartmentDto();
		departmentDto.setId(1L);
		departmentDto.setName("Software Engineer");
	    when(departmentService.save(DepartmentDto.parse(departmentDto))).thenReturn(departmentDto);
		this.mockMvc.perform(post("/admin/department/save").flashAttr("department", departmentDto))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/admin/department/search"));
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities="ADMIN")
	void testDeleteDepartment() throws Exception {
		//DepartmentDto departmentDto=new DepartmentDto();
		when(departmentService.deleteById(1L)).thenReturn(true);
		this.mockMvc.perform(get("/admin/department/delete").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/admin/department/search"));
		//fail("Not yet implemented");
	}

}

package team.ojt7.recruitment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.service.PositionService;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PositionService positionService;

	@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN,GENERAL_MANAGER")
	void testAddNewPosition() throws Exception{
		this.mockMvc.perform(get("/admin/position/add"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-position"))
		.andExpect(model().attributeExists("position"));
	}

	@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN,GENERAL_MANAGER")
	void testSearchPositions() throws Exception {
		
		List<PositionDto> positionDtos = new ArrayList<>();
		PositionDto positionDto=new PositionDto();
		
		positionDto.setId(Long.valueOf(1));
		positionDto.setName("Software Engineer");
		positionDto.setDeleted(false);
		
		positionDtos.add(positionDto);
		
		this.mockMvc.perform(get("/manager/position/search").flashAttr("user", positionDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("positions"));
		
	}

	@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN,GENERAL_MANAGER")
	void testEditPosition() throws Exception {
		
		this.mockMvc.perform(get("/admin/position/edit").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-position"))
		.andExpect(model().attributeExists("position"));
	}

	@Disabled
	@Test
	@WithMockUser(authorities  = "ADMIN,GENERAL_MANAGER")
	void testSavePosition() throws Exception {
		Position positionDto=new Position();
		
		positionDto.setId(Long.valueOf(1));
		positionDto.setName("Software Engineer");
		positionDto.setDeleted(false);
		
		this.mockMvc.perform(post("/admin/position/save").flashAttr("position", positionDto))
		.andExpect(status().isOk())
		.andExpect(redirectedUrl("/manager/position/search"));
	}

	@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN,GENERAL_MANAGER")
	void testDeletePosition() throws Exception {
		this.mockMvc.perform(get("/admin/position/delete").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(redirectedUrl("/manager/position/search"));
	}

}

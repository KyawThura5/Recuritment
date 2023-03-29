package team.ojt7.recruitment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.service.PositionService;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)

class PositionControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PositionService positionService;


	//@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN,GENERAL_MANAGER")
	void testSearchPositions() throws Exception {
		
		List<PositionDto> positionDtos = new ArrayList<>();
		PositionDto positionDto=new PositionDto();
		
		positionDto.setId(Long.valueOf(1));
		positionDto.setName("Software Engineer");
		positionDto.setDeleted(false);
		
		positionDtos.add(positionDto);
		
		//when(positionService.search("%So%")).thenReturn(positionDtos);
		
		this.mockMvc.perform(get("/manager/position/search").flashAttr("user", positionDtos));
		
	}

	//@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditPosition() throws Exception {
		
		PositionDto positionDto=new PositionDto();
		
		Position position = new Position();
		
		position.setId(Long.valueOf(1));
		position.setName("Software Engineer");
		position.setDeleted(false);
		
		positionDto.setId(position.getId());
		positionDto.setName(position.getName());
		positionDto.setDeleted(position.isDeleted());
		
		Long id = positionDto.getId();
		
		
		
		//when(positionService.findById(positionDto.getId())).thenReturn(Optional.of(positionDto));
		
		
		this.mockMvc.perform(get("/admin/position/edit").param("id", "1"));
		
	}

	//@Disabled
	@Test
	@WithMockUser(authorities  = "ADMIN")
	void testSavePosition() throws Exception {
		Position position=new Position();
		
		position.setId(Long.valueOf(1));
		position.setName("Software Engineer");
		position.setDeleted(false);
		
		PositionDto positionDto = new PositionDto();
		positionDto.setId(position.getId());
		positionDto.setName(position.getName());
		positionDto.setDeleted(position.isDeleted());
		
		//when(positionService.save(position)).thenReturn(positionDto);
		
		this.mockMvc.perform(post("/admin/position/save").flashAttr("position", position));
	}

	//@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeletePosition() throws Exception {
		Position position=new Position();
		
		position.setId(Long.valueOf(1));
		position.setName("Software Engineer");
		position.setDeleted(false);
		
		PositionDto positionDto = new PositionDto();
		positionDto.setId(position.getId());
		positionDto.setName(position.getName());
		positionDto.setDeleted(position.isDeleted());
		
		
	//when(positionService.deleteById(positionDto.getId())).thenReturn(true);
		
		this.mockMvc.perform(get("/admin/position/delete").param("id", "1"));
	}

}

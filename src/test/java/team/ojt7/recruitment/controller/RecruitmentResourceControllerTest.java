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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.DirectRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.service.DirectRecruitmentResourceService;
import team.ojt7.recruitment.model.service.ExternalRecruitmentResourceService;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers =RecruitmentResourceController.class)
class RecruitmentResourceControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	@Qualifier("RecruitmentResource")
	private RecruitmentResourceService recruitmentResourceService;
	
	@MockBean
	@Qualifier("direct")
	private DirectRecruitmentResourceService directRecruitmentResourceService;
	
	@MockBean
	@Qualifier("external")
	private ExternalRecruitmentResourceService externalRecruitmentResourceService;

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSearchExternalRecruitmentResources() throws Exception {
		List<RecruitmentResourceDto> recruitDtos=new ArrayList<>();
		RecruitmentResourceDto recruitDto=new RecruitmentResourceDto();
		recruitDto.setId(1L);
		recruitDto.setCode("R001");
		recruitDto.setName("External Recruit");
		recruitDto.setDeleted(false);
		recruitDtos.add(recruitDto);
		when(recruitmentResourceService.search("External Recruit","ExternalRecruitmentResource")).thenReturn(recruitDtos);
		this.mockMvc.perform(get("/manager/recruitment/external/search").flashAttr("recruitmentResource", recruitDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("external-recruitment-resources"));
		//fail("Not yet implemented");
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSearchDirectRecruitmentResources() throws Exception {
		List<RecruitmentResourceDto> recruitDtos=new ArrayList<>();
		RecruitmentResourceDto recruitDto=new RecruitmentResourceDto();
		recruitDto.setId(1L);
		recruitDto.setCode("R001");
		recruitDto.setName("Direct Recruit");
		recruitDto.setDeleted(false);
		recruitDtos.add(recruitDto);
		when(recruitmentResourceService.search("Direct Recruit","DirectRecruitmentResource")).thenReturn(recruitDtos);
		this.mockMvc.perform(get("/manager/recruitment/direct/search").flashAttr("recruitmentResource", recruitDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("direct-recruitment-resources"));
		
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditExternalRecruitmentResource() throws Exception {
		ExternalRecruitmentResourceDto exRecruitDto=new ExternalRecruitmentResourceDto();
		when(recruitmentResourceService.findById(exRecruitDto.getId())).thenReturn(Optional.of(exRecruitDto));
		this.mockMvc.perform(get("/hr/recruitment/external/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-external-recruitment-resource"));
		
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditDirectRecruitmentResource() throws Exception {
		DirectRecruitmentResourceDto dirRecruitDto=new DirectRecruitmentResourceDto();
		when(recruitmentResourceService.findById(dirRecruitDto.getId())).thenReturn(Optional.of(dirRecruitDto));
		this.mockMvc.perform(get("/hr/recruitment/direct/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("edit-direct-recruitment-resource"));
		
	}
    @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSaveExternalRecruitmentResource() throws Exception {
		RecruitmentResourceDto recruitDto=new RecruitmentResourceDto();
		recruitDto.setId(1L);
		recruitDto.setCode("R001");
		recruitDto.setName("External Recruit");
		recruitDto.setDeleted(false);
		when(recruitmentResourceService.save(RecruitmentResourceDto.parse(recruitDto))).thenReturn(recruitDto);
		this.mockMvc.perform(post("/hr/recruitment/external/save").flashAttr("recruitmentResource",recruitDto))
		.andExpect(status().isOk())
		.andExpect(redirectedUrl("/manager/recruitment/external/search"));
	}
    @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSaveDirectRecruitmentResource() throws Exception {
		RecruitmentResourceDto recruitDto=new RecruitmentResourceDto();
		recruitDto.setId(1L);
		recruitDto.setCode("R001");
		recruitDto.setName("Direct Recruit");
		recruitDto.setDeleted(false);
		when(recruitmentResourceService.save(RecruitmentResourceDto.parse(recruitDto))).thenReturn(recruitDto);
		this.mockMvc.perform(post("/hr/recruitment/direct/save").flashAttr("recruitmentResource",recruitDto))
		.andExpect(status().isOk())
		.andExpect(redirectedUrl("/manager/recruitment/direct/search"));
		
	}

	@Test
	void testShowExternalRecruitmentResourceDetail() {
		
	}

	@Test
	void testShowDirectRecruitmentResourceDetail() {
		
	}

	@Test
	void testDeleteExternalRecruitmentResource() {
		
	}

	@Test
	void testDeleteDirectRecruitmentResource() {
		
	}

}

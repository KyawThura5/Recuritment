package team.ojt7.recruitment.model.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;

import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;


@SpringBootTest
public class RecruitmentResourceServiceTest {
	@InjectMocks
	private RecruitmentResourceServiceImpl recruitmentResourceService;
	@Mock
	private RecruitmentResourceRepo recruitmentresourceRepo;
	
	@Test
	public void testSave() {
		
		RecruitmentResource recruitmentResource=new RecruitmentResource();
		recruitmentResource.setId(1L);
		recruitmentResource.setCode("R001");
	    recruitmentResource.setName("Direct");
	    recruitmentResource.setDeleted(false);
	    when(recruitmentresourceRepo.save(recruitmentResource)).thenReturn(recruitmentResource);
		recruitmentResourceService.save(recruitmentResource);
		verify(recruitmentresourceRepo,times(1)).save(recruitmentResource);
		//verify(recruitmentresourceRepo,times(1)).save(team);
	
		
		
	}
	@Disabled
	@Test
	public void testSearh() {
		List<RecruitmentResource> resultList=new ArrayList<>();
		RecruitmentResource recruitmentResource=new RecruitmentResource();
		recruitmentResource.setId(1L);
		recruitmentResource.setCode("R001");
		recruitmentResource.setName("Direct");
		recruitmentResource.setDeleted(false);
		resultList.add(recruitmentResource);
		when(recruitmentresourceRepo.search("%Di%")).thenReturn(resultList);
		List<RecruitmentResourceDto> resources=recruitmentResourceService.search("Direct");
		assertEquals(1,resources.size());
		verify(recruitmentresourceRepo,times(1)).search("%Di%");
		
		
	}
	
	@Test
	public void testSearhTwoArguments() {
		List<RecruitmentResource> resultList=new ArrayList<>();
		RecruitmentResource recruitmentResource=new RecruitmentResource();
		recruitmentResource.setId(1L);
		recruitmentResource.setCode("R001");
		recruitmentResource.setName("Direct");
		recruitmentResource.setDeleted(false);
		resultList.add(recruitmentResource);
		when(recruitmentresourceRepo.search("%R001%","DirectRecruitmentResource")).thenReturn(resultList);
		List<RecruitmentResourceDto> resources=recruitmentResourceService.search("R001","DirectRecruitmentResource");
		assertEquals(1,resources.size());
		verify(recruitmentresourceRepo,times(1)).search("%R001%","DirectRecruitmentResource");
		
		
	}
	@Test
	public void testFindById() {
		RecruitmentResource recruitmentResource=new RecruitmentResource();
		recruitmentResource.setId(1L);
		recruitmentResource.setCode("R001");
		recruitmentResource.setName("Direct");
		when(recruitmentresourceRepo.findById(1L)).thenReturn(Optional.of(recruitmentResource));
		RecruitmentResourceDto recruitmentDto=recruitmentResourceService.findById(1L).get();
		assertEquals("Direct",recruitmentDto.getName());
	}
	@Test
	public void testDeleteById() {
		recruitmentResourceService.deleteById(1L);
		verify(recruitmentresourceRepo,times(1)).deleteById(1L);
	}

}

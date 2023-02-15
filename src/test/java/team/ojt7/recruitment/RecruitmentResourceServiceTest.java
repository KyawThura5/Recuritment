package team.ojt7.recruitment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.impl.RecruitmentResourceServiceImpl;

@SpringBootTest
public class RecruitmentResourceServiceTest {
	@InjectMocks
	private RecruitmentResourceServiceImpl recruitmentResourceService;
	@Mock
	private RecruitmentResourceRepo recruitmentresourceRepo;
	@Test
	public void testSave() {
//		RecruitmentResource recruitmentResource=new RecruitmentResource();
//		recruitmentResource.setId(1L);
//		recruitmentResource.setCode("R001");
//		recruitmentResource.setName("Direct");
//		recruitmentResourceService.save(recruitmentResource);
//		verify(recruitmentresourceRepo,times(1)).save(recruitmentResource);
	
		
		
	}
	@Test
	public void testSearch() {
		List<RecruitmentResource> resultList=new ArrayList<>();
		RecruitmentResource recruitmentResource1=new RecruitmentResource();
		recruitmentResource1.setId(1L);
		recruitmentResource1.setCode("R001");
		recruitmentResource1.setName("Direct");
		RecruitmentResource recruitmentResource2=new RecruitmentResource();
		recruitmentResource2.setId(2L);
		recruitmentResource2.setCode("R002");
		recruitmentResource2.setName("Redirect");
		resultList.add(recruitmentResource1);
		resultList.add(recruitmentResource2);
		when(recruitmentresourceRepo.search("%"+"Direct"+"%")).thenReturn(resultList);
		List<RecruitmentResourceDto> resources=recruitmentResourceService.search("%"+"Direct"+"%");
		assertEquals(1,resources.size());
		verify(recruitmentresourceRepo,times(1)).search("%"+"Direct"+"%");
	}
	@Test
	public void testSearhForTwoParameter() {
		
	}
	@Test
	public void testFindById() {
		
	}
	@Test
	public void testDeleteById() {
		
	}

}

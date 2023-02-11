package team.ojt7.recruitment.repo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.entity.ExternalRecruitmentResource;
import team.ojt7.recruitment.model.entity.ExternalRecruitmentResource.Type;
import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;

@SpringBootTest
public class RecruitmentResourceTest {
	
	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;

	@Test
	public void test() {
		ExternalRecruitmentResource err = new ExternalRecruitmentResource();
		err.setCode("DATAG001");
		err.setName("Job.com");
		err.setPic("U Kyaw Kyaw");
		err.setContactPerson("U Naing");
		err.setEmail("job@gmail.com");
		err.setPhone("099999999");
		err.setType(Type.AGENCY);
		err.setWebsiteLink("job.com");
		err.setAddress("Yangon");
		
		ExternalRecruitmentResource savedErr = recruitmentResourceRepo.save(err);
		
		ExternalRecruitmentResource err1 = (ExternalRecruitmentResource) recruitmentResourceRepo.findById(1L).get();
		
		List<RecruitmentResource> list = recruitmentResourceRepo.search("%job%");
		
		ExternalRecruitmentResource err2 = (ExternalRecruitmentResource) recruitmentResourceRepo.findById(null).orElse(null);
	}
}

package team.ojt7.recruitment.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.repo.ApplicantStatusChangeHistoryRepo;

@SpringBootTest
public class ApplicantStatusChangeHistoryTest {

	@Autowired
	private ApplicantStatusChangeHistoryRepo repo;
	
	@Test
	void test() {
		var list = repo.findByAppliacantId(1L);
	}
}

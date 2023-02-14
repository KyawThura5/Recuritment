package team.ojt7.recruitment.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;

@SpringBootTest
public class RecruitmentResourceRepoTest {

	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;
	
	@ParameterizedTest
	@Sql({
		"/truncate-tables.sql",
		"/pre-insert-recruitment-resources-1.sql"
	})
	@CsvSource({
		"job,2", "com,1", "co,1", "net,1", "connect,0", "24,1", "4,1", "4u,0", "ace,0", "ship,1"
	})
	public void testSearchByKeyword(String keyword, int count) {
		List<RecruitmentResource> rrs = recruitmentResourceRepo.search("%" + keyword + "%");
		assertEquals(count, rrs.size());
	}
	
	@ParameterizedTest
	@Sql({
		"/truncate-tables.sql",
		"/pre-insert-recruitment-resources-1.sql"
	})
	@CsvSource({
		"job,ExternalRecruitmentResource,2", "job,DirectRecruitmentResource,0",
		"com,ExternalRecruitmentResource,1", "com,DirectRecruitmentResource,0",
		"net,ExternalRecruitmentResource,1", "net,DirectRecruitmentResource,0",
		"connect,ExternalRecruitmentResource,0", "connect,DirectRecruitmentResource,0",
		"24,ExternalRecruitmentResource,1", "24,DirectRecruitmentResource,0",
		"4,ExternalRecruitmentResource,1", "4,DirectRecruitmentResource,0",
		"ace,ExternalRecruitmentResource,0", "ace,DirectRecruitmentResource,0",
		"ship,ExternalRecruitmentResource,0", "ship,DirectRecruitmentResource,1",
	})
	public void testSearchByKeywordAndEntityType(String keyword, String entityType, int count) {
		List<RecruitmentResource> rrs = recruitmentResourceRepo.search("%" + keyword + "%", entityType);
		assertEquals(count, rrs.size());
	}
	
	@Test
	@Sql({
		"/truncate-tables.sql",
		"/pre-insert-recruitment-resources-1.sql"
	})
	public void testDeleteById() {
		Long id = 1L;
		RecruitmentResource beforeDelete = recruitmentResourceRepo.findById(id).get();
		recruitmentResourceRepo.deleteById(id);
		RecruitmentResource afterDelete = recruitmentResourceRepo.findById(id).get();
		assertFalse(beforeDelete.isDeleted());
		assertTrue(afterDelete.isDeleted());
	}
	
}

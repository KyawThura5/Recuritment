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

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;

@SpringBootTest
public class UserRepoTest {

	@Autowired
	private UserRepo userRepo;
	
	@ParameterizedTest
	@Sql(scripts = {
		"/truncate-tables.sql",
		"/pre-insert-users-1.sql"
	})
	@CsvSource(
		{
			"tun,,3", "tun,ADMIN,1", "tun,GENERAL_MANAGER,1", "tun,PROJECT_MANAGER,1", "tun,HIRING_MANAGER,0",
			"tuntun,,1", "tuntun,ADMIN,1", "tuntun,HIRING_MANAGER,0", "tuntun,GENERAL_MANAGER,0",
			"su,,2", "su,HIRING_MANAGER,1", "su,DEPARTMENT_HEAD,1", "su,ADMIN,0",
			"susu,,1", "susu,HIRING_MANAGER,1", "susu,DEPARTMENT_HEAD,0",
			"kyaw,,1", "kyaw,GENERAL_MANAGER,1", "kyaw,HIRING_MANAGER,0",
			"001,,2", "001,ADMIN,1", "001,GENERAL_MANAGER,1", "001,HIRING_MANAGER,0",
			"thidar,,2", "thidar,DEPARTMENT_HEAD,2", "thidar,GENERAL_MANAGER,0",
			"aung,,2", "aung,DEPARTMENT_HEAD,2", "aung,HIRING_MANAGER,0", "aung,PROJECT_MANAGER,0",
			"thidaraung,,1", "thidaraung,DEPARTMENT_HEAD,1", "thidaraung,GENERAL_MANAGER,0",
			"652,,2", "652,ADMIN,1", "652,HIRING_MANAGER,1", "652,PROJECT_MANAGER,0",
			"emp002,,1", "emp002,HIRING_MANAGER,1", "emp002,GENERAL_MANAGER,0",
			"aungko,,0", "aung kyaw,,0", "nyein,,0"
		}
	)
	public void testSearchByKeywordAndRole(String keyword, Role role, int count) {
		List<User> users = userRepo.search("%" + keyword + "%", role);
		assertEquals(count, users.size());
	}
	
	@Test
	@Sql(scripts = {
		"/truncate-tables.sql",
		"/pre-insert-users-1.sql"
	})
	public void testDeleteById() {
		Long id = 1L;
		User beforeDelete = userRepo.findById(id).get();
		userRepo.deleteById(id);
		User afterDelete = userRepo.findById(id).get();
		
		assertFalse(beforeDelete.isDeleted());
		assertTrue(afterDelete.isDeleted());
	}
	
}

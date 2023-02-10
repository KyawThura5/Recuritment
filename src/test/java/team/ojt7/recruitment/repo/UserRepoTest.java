package team.ojt7.recruitment.repo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;

@SpringBootTest
public class UserRepoTest {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	public void test1() {
		User user = new User();
		user.setCode("EMP001");
		user.setName("Employee 1");
		user.setEmail("emp1@gmail.com");
		user.setPhone("09222222222");
		user.setPassword("emp1111");
		user.setRole(Role.ADMIN);
		
		User savedUser = userRepo.save(user);
		assertNotNull(savedUser);
		
		userRepo.deleteById(savedUser.getId());
		
		List<User> users = userRepo.search("%emp%", null);
	}
}

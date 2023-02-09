package team.ojt7.recruitment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.repo.UserRepo;
import team.ojt7.recruitment.model.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepo userRepo;
	
	@Test
	public void testSearch() {
		List<User> resultList = new ArrayList<>();
		User user = new User();
		resultList.add(user);
		
		when(userRepo.search("Mg", null)).thenReturn(resultList);
		
		userService.search("Mg", null);
		
		verify(userRepo, times(1)).search("Mg", null);
	}
	
	@Test
	public void testFindById() {
		User user = new User();
		
//		when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
//		userService.findById(1)
//		verify(userRepo, times(1)).findById();
	}
}

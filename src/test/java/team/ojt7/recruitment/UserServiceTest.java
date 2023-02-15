package team.ojt7.recruitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.Gender;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
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
		User user1 = new User();
		user1.setCode("U001");
		user1.setName("Mg Mg");
		user1.setEmail("mgmg@gmail.com");
		user1.setPhone("09873234567");
		user1.setRole(Role.ADMIN);
		user1.setGender(Gender.MALE);
		user1.setPassword("mgmg1234");
		User user2 = new User();
		user2.setCode("U002");
		user2.setName("Aung");
		user2.setEmail("aung@gmail.com");
		user2.setPhone("09873234567");
		user2.setRole(Role.DEPARTMENT_HEAD);
		user2.setGender(Gender.MALE);
		user2.setPassword("aungaung");
		resultList.add(user1);
		resultList.add(user2);
		
		
		
		when(userRepo.search("%Mg Mg%",Role.ADMIN)).thenReturn(resultList);
		List<UserDto> userList=userService.search("Mg Mg",Role.ADMIN);
		assertEquals(2,userList.size());
		verify(userRepo,times(1)).search("%Mg Mg%",Role.ADMIN);
		
		
//		verify(userRepo, times(1)).search("Mg", null);
	}
	
	@Test
	public void testFindById() {
		User user = new User();
		user.setCode("U001");
		user.setName("Mg Mg");
		user.setEmail("mgmg@gmail.com");
		user.setPhone("09873234567");
		user.setRole(Role.ADMIN);
		user.setGender(Gender.MALE);
		user.setPassword("mgmg1234");
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		UserDto getUser=userService.findById(user.getId()).get();
		assertEquals("U001",getUser.getCode());
		assertEquals("Mg Mg",getUser.getName());
		assertEquals("mgmg@gmail.com",getUser.getEmail());
		assertEquals("09873234567",getUser.getPhone());
		assertEquals(Role.ADMIN,getUser.getRole());
		assertEquals(Gender.MALE,getUser.getGender());
		assertEquals("mgmg1234",getUser.getPassword());
		
//		when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
//		userService.findById(1)
//		verify(userRepo, times(1)).findById();
	}
	@Test
	public void testSave() {
		User user = new User();
		user.setCode("U001");
		user.setName("Mg Mg");
		user.setEmail("mgmg@gmail.com");
		user.setPhone("09873234567");
		user.setRole(Role.ADMIN);
		user.setGender(Gender.MALE);
		user.setPassword("mgmg1234");
		userService.save(user);
		verify(userRepo,times(1)).save(user);
		
	}
	@Test
	public void testDeleteById() {
		userService.deleteById(1L);
		verify(userRepo,times(1)).deleteById(1L);
	}
}

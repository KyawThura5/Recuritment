package team.ojt7.recruitment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.Gender;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddNewUser() throws Exception {
		when(userService.generateNewWithCode()).thenReturn(new UserDto());
		this.mockMvc.perform(get("/admin/user/add"))
		.andExpect(status().isOk())
		.andExpect(view().name("adduser"))
		.andExpect(model().attributeExists("user"));
	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditUser() throws Exception {
		UserDto userDto = new UserDto();
		User user = new User();

		Gender gender = null;

		user.setId(Long.valueOf(1));
		user.setCode("c1");
		user.setName("Khin");
		user.setEmail("khin@gmail.com");
		user.setGender(gender.FEMALE);
		user.setPhone("09955049889");
		user.setRole(user.getRole().ADMIN);
		user.setPassword("12345678");
		
		userDto.setId(user.getId());
		userDto.setCode(user.getCode());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setGender(user.getGender());
		userDto.setPhone(user.getPhone());
		userDto.setRole(user.getRole());
		userDto.setPassword(user.getPassword());
		userDto.setConfirmPassword(user.getPassword());
		
		 when(userService.findById(user.getId())).thenReturn(Optional.of(userDto));

		this.mockMvc.perform(get("/admin/user/edit").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("edituser"))
		.andExpect(model().attributeExists("user"));
	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSaveUser() throws Exception {

		UserDto userDto = new UserDto();
		

		Gender gender = null;

		userDto.setId(Long.valueOf(1));
		userDto.setCode("c1");
		userDto.setName("Khin");
		userDto.setEmail("khin@gmail.com");
		userDto.setGender(gender.FEMALE);
		userDto.setPhone("09955049889");
		userDto.setRole(userDto.getRole().ADMIN);
		userDto.setPassword("12345678");
		userDto.setConfirmPassword("12345678");
		
		User user = UserDto.parse(userDto);

		if (user.getPassword().equals(userDto.getConfirmPassword())) {
			
			 when(userService.save(user)).thenReturn(userDto);

			this.mockMvc.perform(post("/admin/user/save").flashAttr("user", user))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/admin/user/search"));
		}

	}

//	@Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testShowUserDetail() throws Exception {
		
		UserDto userDto = new UserDto();
		
		

		Gender gender = null;

		userDto.setId((Long.valueOf(1)));
		userDto.setCode("c1");
		userDto.setName("Khin");
		userDto.setEmail("khin@gmail.com");
		userDto.setGender(gender.FEMALE);
		userDto.setPhone("09955049889");
		userDto.setRole(userDto.getRole().ADMIN);
		userDto.setPassword("12345678");
		userDto.setConfirmPassword("12345678");
		
		Long id=userDto.getId();
		

		Optional<UserDto> userDtos=userService.findById(id);


		if (userDtos.isPresent()) {
			this.mockMvc.perform(get("/admin/user/detail").param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("userdetail"));
		}

		

	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteUser() throws Exception {
		
		UserDto userDto = new UserDto();
		User user = new User();

		Gender gender = null;

		user.setId(Long.valueOf(1));
		user.setCode("c1");
		user.setName("Khin");
		user.setEmail("khin@gmail.com");
		user.setGender(gender.FEMALE);
		user.setPhone("09955049889");
		user.setRole(user.getRole().ADMIN);
		user.setPassword("12345678");
		
		userDto.setId(user.getId());
		userDto.setCode(user.getCode());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setGender(user.getGender());
		userDto.setPhone(user.getPhone());
		userDto.setRole(user.getRole());
		userDto.setPassword(user.getPassword());
		userDto.setConfirmPassword(user.getPassword());
		
		 when(userService.deleteById(userDto.getId())).thenReturn(true);
		
		this.mockMvc.perform(get("/admin/user/delete").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/admin/user/search"));
	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testSearchUsers() throws Exception {

		List<UserDto> userDtos = new ArrayList<>();
		UserDto userDto = new UserDto();

		Gender gender = null;

		userDto.setId(Long.valueOf(1));
		userDto.setCode("c1");
		userDto.setName("Khin");
		userDto.setEmail("khin@gmail.com");
		userDto.setGender(gender.FEMALE);
		userDto.setPhone("09955049889");
		userDto.setRole(userDto.getRole().ADMIN);
		userDto.setPassword("12345678");
		userDto.setConfirmPassword("12345678");

		userDtos.add(userDto);

		when(userService.search("%Khin%", userDto.getRole().ADMIN)).thenReturn(userDtos);
		
		this.mockMvc.perform(get("/admin/user/search").flashAttr("user", userDtos))
		.andExpect(status().isOk())
		.andExpect(view().name("users"));

	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testShowUserprofile() throws Exception {
		this.mockMvc.perform(get("/admin/user/profile"))
		.andExpect(status().isOk())
		.andExpect(view().name("userprofile"));

	}

	// @Disabled
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditprofile() throws Exception {
		this.mockMvc.perform(get("/admin/user/editprofile"))
		.andExpect(status().isOk())
		.andExpect(view().name("editprofile"));

	}

}

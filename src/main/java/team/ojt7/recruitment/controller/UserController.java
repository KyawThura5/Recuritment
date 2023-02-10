package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
	public String addNewUser(ModelMap model) {
		model.addAttribute("user",new UserDto());		
		return "adduser";
	}

	@RequestMapping(value = "/admin/user/edit", method = RequestMethod.GET)
	public String editUser(ModelMap model) {
		model.addAttribute("user",new UserDto());
		return "edituser";

	}

	@RequestMapping(value="/admin/user/save",method=RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") UserDto dto,ModelMap model) {
		User user=new User();
		user.setCode(dto.getCode());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());
		user.setGender(dto.getGender());
		user.setPhone(dto.getPhone());
		user.setPassword(dto.getPassword());
		userService.save(user);
		return "redirect:/admin/user/search";
	}

	@RequestMapping(value = "admin/user/detail", method = RequestMethod.GET)
	public String showUserDetail() {
		return "userdetail";

	}

	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.GET)
	public String deleteUser() {
		return "users";
	}

	@RequestMapping(value = "/admin/user/search", method = RequestMethod.GET)
	public String searchUsers(
			@RequestParam(required=false)
			String keyword,
			ModelMap model) {
		List<UserDto> list=userService.search(keyword,null);
		model.addAttribute("list",list);
		return "users";

	}
	@RequestMapping(value = "/admin/user/profile", method = RequestMethod.GET)
	public String showUserprofile() {
		return "userprofile";

	}
	@RequestMapping(value = "/admin/user/editprofile", method = RequestMethod.GET)
	public String Editprofile() {
		return "editprofile";

	}

}
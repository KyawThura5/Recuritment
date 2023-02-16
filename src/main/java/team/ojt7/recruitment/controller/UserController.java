package team.ojt7.recruitment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.AdminChangePasswordFormDto;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.UserService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.model.validator.AdminChangePasswordFormValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminChangePasswordFormValidator adminChangePasswordFormValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		var target = binder.getTarget();
		if (target != null && adminChangePasswordFormValidator.supports(target.getClass())) {
			binder.addValidators(adminChangePasswordFormValidator);
		}
	}

	@RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
	public String addNewUser(ModelMap model) {
		model.addAttribute("user", userService.generateNewWithCode());		
		return "adduser";
	}

	@RequestMapping(value = "/admin/user/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam("id")Long id, ModelMap model) {
		model.put("user", userService.findById(id).get());
		return "edituser";

	}

	@RequestMapping(value="/admin/user/save",method=RequestMethod.POST)
	public String saveUser(
			@Validated 
			@ModelAttribute("user")
			UserDto dto,
			BindingResult bs,
			ModelMap model) {
		
		if(!dto.getPassword().equals(dto.getConfirmPassword())) {
			bs.rejectValue("confirmPassword", "notSame", "Passwords are not the same");
		}
		
		if (!bs.hasErrors()) {
			try {
				User user = UserDto.parse(dto);
				userService.save(user);
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			return dto.getId() == null ? "adduser" : "edituser";
		}
		
		return "redirect:/admin/user/search";
	}
	

	@RequestMapping(value = "admin/user/detail", method = RequestMethod.GET)
	public String showUserDetail(@RequestParam("id") Long id,ModelMap model) {
		Optional<UserDto> user=userService.findById(id);
		if (user.isPresent()) {
			
			model.addAttribute("list",user.get());
			
		}
		
		return "userdetail";

	}

	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("id")Long id) {
		userService.deleteById(id);
		return "redirect:/admin/user/search";
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
	
	@GetMapping("/admin/user/password/change")
	public String showAdminChangePasswordPage(
			@RequestParam Long id,
			ModelMap model) {
		AdminChangePasswordFormDto form = new AdminChangePasswordFormDto();
		form.setUserId(id);
		model.put("passwordForm", form);
		return "change-user-password";
	}
	
	@GetMapping("/user/password/change")
	public String showUserChangePasswordPage(
			@RequestParam
			Long id
			) {
		return "change-password";
	}
	
	@PostMapping("/admin/user/password/save")
	public String savePassword(
			@Validated
			@ModelAttribute("passwordForm")
			AdminChangePasswordFormDto passwordForm,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			return "change-user-password";
		}
		
		userService.changePassword(passwordForm.getUserId(), passwordForm.getPassword());
		return "redirect:/admin/user/search";
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
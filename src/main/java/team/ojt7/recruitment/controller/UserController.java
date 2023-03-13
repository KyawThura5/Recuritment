package team.ojt7.recruitment.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.AdminChangePasswordFormDto;
import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.UserChangePasswordFormDto;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.dto.UserSearch;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.UserService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.model.service.exception.ServiceException;
import team.ojt7.recruitment.model.validator.AdminChangePasswordFormValidator;
import team.ojt7.recruitment.model.validator.UserChangePasswordFormValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminChangePasswordFormValidator adminChangePasswordFormValidator;
	
	@Autowired
	private UserChangePasswordFormValidator userChangePasswordFormValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		var target = binder.getTarget();
		if (target != null) {
			if (adminChangePasswordFormValidator.supports(target.getClass())) {
				binder.addValidators(adminChangePasswordFormValidator);
			}
			
			if (userChangePasswordFormValidator.supports(target.getClass())) {
				binder.addValidators(userChangePasswordFormValidator);
			}
			
		}
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String addNewUser(ModelMap model) {
		model.addAttribute("user", userService.generateNewWithCode());		
		return "adduser";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam("id")Long id, ModelMap model) {
		model.put("user", userService.findById(id).get());
		return "edituser";

	}

	@RequestMapping(value="/user/save",method=RequestMethod.POST)
	public String saveUser(
			@Validated
			@ModelAttribute("user")
			UserDto dto,
			BindingResult bs,
			RedirectAttributes redirect,
			ModelMap model) {
		
		if(!dto.getPassword().equals(dto.getConfirmPassword())) {
			bs.rejectValue("confirmPassword", "notSame", "Passwords are not the same");
		}
		
		if (!bs.hasErrors()) {
			try {
				User user = UserDto.parse(dto);
				userService.save(user);
				String message = dto.getId() == null ? "Successfully create a new user." : "Successfully updated the user.";
				String cssClass = dto.getId() == null ? "notice-success" : "notice-info";
				Alert alert = new Alert(message, cssClass);
				redirect.addFlashAttribute("alert", alert);
				
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			return dto.getId() == null ? "adduser" : "edituser";
		}
		
		
		return "redirect:/user/search";
	}

	@RequestMapping(value = "/user/detail", method = RequestMethod.GET)
	public String showUserDetail(@RequestParam("id") Long id,ModelMap model) {
		Optional<UserDto> user=userService.findById(id);
		if (user.isPresent()) {
			
			model.addAttribute("list",user.get());
			
		}
		
		return "userdetail";

	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String deleteUser(
			@RequestParam("id")
			Long id,
			RedirectAttributes redirect
			) {
		try {
			userService.deleteById(id);
			Alert alert = new Alert("Successfully deleted the user.", "notice-success");
			redirect.addFlashAttribute("alert", alert);
		} catch (ServiceException e) {
			Alert alert = new Alert(e.getMessage(), "notice-danger");
			redirect.addFlashAttribute("alert", alert);
		}
		
		return "redirect:/user/search";
	}

	@RequestMapping(value = "/user/search", method = RequestMethod.GET)
	public String searchUsers(
			@ModelAttribute("userSearch")
			UserSearch userSearch,
			ModelMap model) {
		Page<UserDto> userPage=userService.search(userSearch);
		model.put("userPage", userPage);
		model.put("userSearch", userSearch);
		return "users";

	}
	
	@GetMapping("/user/password/change")
	public String showAdminChangePasswordPage(
			@RequestParam Long id,
			ModelMap model) {
		AdminChangePasswordFormDto form = new AdminChangePasswordFormDto();
		form.setUserId(id);
		model.put("passwordForm", form);
		return "change-user-password";
	}
	
	@GetMapping("/profile/password/change")
	public String showUserChangePasswordPage(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		UserChangePasswordFormDto form = new UserChangePasswordFormDto();
		form.setUserId(loginUser.getId());
		model.put("passwordForm", form);
		return "change-password";
	}
	
	@PostMapping("/user/password/save")
	public String savePassword(
			@Validated
			@ModelAttribute("passwordForm")
			AdminChangePasswordFormDto passwordForm,
			RedirectAttributes redirect,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			return "change-user-password";
		}
		
		userService.changePassword(passwordForm.getUserId(), passwordForm.getPassword());
		redirect.addFlashAttribute("alert", new Alert("Successfully updated the password.", "notice-info"));
		return "redirect:/user/search";
	}
	
	@PostMapping("/profile/password/save")
	public String savePassword(
			@Validated
			@ModelAttribute("passwordForm")
			UserChangePasswordFormDto passwordForm,
			BindingResult bindingResult
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				userService.changePassword(passwordForm.getUserId(), passwordForm.getOldPassword(), passwordForm.getNewPassword());
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "change-password";
		}
		
		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showUserprofile(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		model.put("user", UserDto.of(loginUser));
		return "userprofile";

	}
	
	@PostMapping("/profile/save")
	public String saveProfile(
			@Validated
			@ModelAttribute("user")
			UserDto user,
			BindingResult bindingResult
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				userService.save(UserDto.parse(user));
			}  catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "editprofile";
		}
		
		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String Editprofile(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		model.put("user", UserDto.of(loginUser));
		return "editprofile";

	}

}
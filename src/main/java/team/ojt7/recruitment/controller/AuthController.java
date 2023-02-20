package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String showLoginPage(@RequestParam String error ,ModelMap model) {
		if (error != null) {
			model.put("loginFormErrorMessage", "Invalid employee code or password");
		}
		return "login";
	}
}

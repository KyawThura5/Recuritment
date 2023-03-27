package team.ojt7.recruitment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String showLoginPage(HttpSession session, ModelMap model) {
		AuthenticationException exception = (AuthenticationException) session.getAttribute("exception");
		if (exception != null) {
			model.put("employeeCode", session.getAttribute("employeeCode"));
			model.put("password", session.getAttribute("password"));
			if (exception instanceof BadCredentialsException) {
				model.put("loginFormErrorMessage", "Invalid employee code or password");
			} else if (exception instanceof DisabledException) {
				model.put("loginFormErrorMessage", "Your account is inactive");
			}
			session.setAttribute("exception", null);
			session.setAttribute("employeeCode", null);
			session.setAttribute("password", null);
		}
		return "login";
	}
	
	@GetMapping("/unauthorized")
	public String showUnauthorizedPage() {
		return "error-page";
	}
}

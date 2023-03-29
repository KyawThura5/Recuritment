package team.ojt7.recruitment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeSerivce;

	@GetMapping({"/", "/home"})
	public String showHomePage(
			ModelMap model,
			HttpSession session
			) {
		User loginUser = (User) session.getAttribute("loginUser");
		model.put("home", loginUser.getRole() == Role.ADMIN ? homeSerivce.getAdminHomeDto() : homeSerivce.getHomeDto());
		return loginUser.getRole() == Role.ADMIN ? "admin-home" : "home";
	}

}

package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import team.ojt7.recruitment.model.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeSerivce;

	@GetMapping({"/", "/home"})
	public String showHomePage(
			ModelMap model
			) {
		model.put("home", homeSerivce.getHomeDto());
		return "home";
	}

}

package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/admin/user")
	public String loginview() {
		return "users";
	}
	
	@GetMapping("/admin/adduser")
	public String adduserview() {
		return "adduser";
	}

}

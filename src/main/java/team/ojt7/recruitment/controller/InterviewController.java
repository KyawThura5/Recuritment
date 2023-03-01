package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import team.ojt7.recruitment.model.service.InterviewService;

@Controller
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	
	@GetMapping("/interview/search")
	public String search() {
		return "interviews";
	}
	
	@GetMapping("/interview/edit")
	public String edit() {
		return "edit-interview";
	}
	
	@PostMapping("/interview/save")
	public String save() {
		return "redirect:/interview/search";
	}
	
	@PostMapping("/interview/delete")
	public String delete() {
		return "redirect:/interview/search";
	}
}

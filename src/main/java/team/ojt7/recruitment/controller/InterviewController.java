package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.service.InterviewService;

@Controller
public class InterviewController {
	
	@Autowired
	InterviewService interviewService;
	
	@GetMapping("/interview/search")
	public String searchInterviews(
			@ModelAttribute("interview")
			ModelMap model) {
		
		return "interviews";
	}

	@GetMapping("/interview/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		
		return null;
	}

	@PostMapping("/interview/save")
	public String saveInterview(@ModelAttribute("interview") @Validated InterviewDto dto,BindingResult bs,ModelMap model) {
		
		
		return null;
	}

	@PostMapping("/interview/delete")
	public String deleteInterview(@RequestParam("id") Long id) {
		
		
		return null;
	}
	
	

}

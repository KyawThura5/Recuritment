package team.ojt7.recruitment.controller;

import java.util.List;

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
			@ModelAttribute("interview")InterviewDto dto,
			@RequestParam(required =false)String keyword,
			ModelMap model) {
		List<InterviewDto> list=interviewService.search(keyword);
		model.addAttribute("list",list);
		return "interviews";
	}

	@GetMapping("/interview/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,@ModelAttribute("interview")InterviewDto dto,
			ModelMap model
			) {
		
		return "edit-interview";
	}

	@PostMapping("/interview/save")
	public String saveInterview(@ModelAttribute("interview") @Validated InterviewDto dto,BindingResult bs,ModelMap model) {
		if(!bs.hasErrors()) {
			interviewService.save(InterviewDto.parse(dto));
		}
		if(bs.hasErrors()) {
			return "edit-interview";
		}
		return "redirect:/interview/search";
	}

	@PostMapping("/interview/delete")
	public String deleteInterview(@RequestParam("id") Long id) {
		
		
		return null;
	}
	
	

}

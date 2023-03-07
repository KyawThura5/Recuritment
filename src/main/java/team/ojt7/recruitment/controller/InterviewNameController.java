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

import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.service.InterviewNameService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class InterviewNameController {
	
	@Autowired
	InterviewNameService interviewService;
	
	@GetMapping("/interviewname/search")
	public String searchInterviews(
			@ModelAttribute("interview")InterviewNameDto dto,
			@RequestParam(required=false)String name,
			ModelMap model) {
		List<InterviewNameDto> list=interviewService.search(name);
		model.addAttribute("list",list);
		return "interview-names";
	}

	@GetMapping("/interviewname/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		InterviewNameDto dto=interviewService.findById(id).orElse(new InterviewNameDto());
		model.put("interview", dto);
		String title=dto.getId()==null ? "Add Title":"Edit Title";
		model.put("title", title);
		return "edit-interview-name";
	}

	@PostMapping("/interviewname/save")
	public String saveInterview(@ModelAttribute("interview") @Validated InterviewNameDto dto,BindingResult bs,ModelMap model) {
		if(!bs.hasErrors()) {
			try {
			interviewService.save(InterviewNameDto.parse(dto));
			}catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
			
		}
		if(bs.hasErrors()) {
			String title=dto.getId()==null ? "Add Title":"Edit Title";
			model.put("title", title);
			return "edit-interview-name";
		}
		return "redirect:/interviewname/search";
	}

	@PostMapping("/interviewname/delete")
	public String deleteInterview(@RequestParam("id")Long id) {
		interviewService.deleteById(id);
		
		return "redirect:/interviewname/search";
	}
	
	

}

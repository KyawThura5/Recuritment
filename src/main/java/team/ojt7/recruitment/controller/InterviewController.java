package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	
	@GetMapping("/interview/search")
	public String searchInterviews(@ModelAttribute("interviewSearch")
	InterviewSearch interviewSearch,
	ModelMap model) {
		Page<InterviewDto> page = interviewService.search(interviewSearch);
		model.put("interviewPage", page);
		model.put("interviewSearch", interviewSearch);
		return "interviews";
	}
	
	@GetMapping("/interview/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			)  {
		InterviewDto interviewDto = interviewService.findById(id).orElse(new InterviewDto());
		model.put("interview", interviewDto);
		String title = interviewDto.getId() == null ? "Add New Interview" : "Edit Interview";
		model.put("title", title);
		
		return "edit-interview";
	}
	
	@PostMapping("/interview/save")
	public String saveInterview(@ModelAttribute("interview") @Validated InterviewDto dto,BindingResult bs,ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				interviewService.save(InterviewDto.parse(dto));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			String title = dto.getId() == null ? "Add New Interview" : "Edit Interview";
			model.put("title", title);
			return "edit-interview";
		}
		
		return "redirect:/interview/search";
	}


	
	@PostMapping("/interview/delete")
	public String deleteInterview(@RequestParam("id") Long id) {
		
		interviewService.deleteById(id);
		return "redirect:/interview/search";
	}
}

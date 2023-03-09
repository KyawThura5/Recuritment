package team.ojt7.recruitment.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.entity.Interview.Status;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.InterviewNameService;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private InterviewNameService interviewNameService;
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private Formatter<InterviewNameDto> interviewNameDtoFormatter;
	
	@Autowired
	private Formatter<ApplicantDto> applicantDtoFormatter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(interviewNameDtoFormatter);
		binder.addCustomFormatter(applicantDtoFormatter);
	}
	
	@GetMapping("/interview/search")
	public String searchInterviews(@ModelAttribute("interviewSearch")
	InterviewSearch interviewSearch,
	ModelMap model) {
		Page<InterviewDto> interviewPage = interviewService.search(interviewSearch);
		model.put("interviewPage", interviewPage);
		model.put("interviewSearch", interviewSearch);
		return "interviews";
	}
	
	@GetMapping("/interview/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			)  {
		InterviewDto interviewDto = interviewService.findById(id).orElse(interviewService.generateNewWithCode());
		model.put("interview", interviewDto);
		model.put("interviewNames",interviewNameService.findAllByIsDeleted(false));
		model.put("applicants", applicantService.findAll());
		String title = interviewDto.getId() == null ? "Create Interview" : "Edit Interview";
		model.put("title", title);
		
		return "edit-interview";
	}
	
	@PostMapping("/interview/save")
	public String saveInterview(@ModelAttribute("interview") @Validated InterviewDto dto,BindingResult bs,ModelMap model,RedirectAttributes redirect) {
		
		if (!bs.hasErrors()) {
			try {
				interviewService.save(InterviewDto.parse(dto));
				String message=dto.getId()==null ? "Successfully created a new interview!" : "Successfully update interview!";
				String cssClass=dto.getId()==null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			String title = dto.getId() == null ? "Create Interview" : "Edit Interview";
			model.put("title", title);
			return "edit-interview";
		}
		
		return "redirect:/interview/search";
	}


	
	@PostMapping("/interview/delete")
	public String deleteInterview(@RequestParam("id") Long id,RedirectAttributes redirect) {
		interviewService.deleteById(id);
		redirect.addFlashAttribute("alert",new Alert("Successfully delete the interview!","notice-success"));
		return "redirect:/interview/search";
	}
	
	@PostMapping("/interview/status/save")
	public String statuschange(@RequestParam("id")Long id,@RequestParam("status")Status status,@RequestParam("comment")String comment,ModelMap map) {
		InterviewDto dto=interviewService.findByIdStatus(id,status,comment).get();		
		interviewService.save(InterviewDto.parse(dto));		
		return "redirect:/interview/search";
		
	}
}

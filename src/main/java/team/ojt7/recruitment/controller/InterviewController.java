package team.ojt7.recruitment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.Formatter;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
		model.put("interviewNames",interviewNameService.findAllForInterview(interviewDto));
		List<ApplicantDto> applicants = interviewDto.getId() == null ? applicantService.getAllAvailableForNewInterview() : List.of(interviewDto.getApplicant());
		model.put("applicants", applicants);
		String title = interviewDto.getId() == null ? "Create Interview" : "Edit Interview";
		model.put("title", title);
		model.put("contextPage", "/interview/search");
		
		return "edit-interview";
	}
	
	@GetMapping("/interview/applicant/edit")
	public String editInterviewFromApplicant (
			@RequestParam(required = false)
			Long applicantId,
			ModelMap model
			)  {
		InterviewDto interviewDto = interviewService.generateNewWithCode();
		ApplicantDto applicantDto = applicantService.findById(applicantId).orElse(null);
		interviewDto.setApplicant(applicantDto);
		
		model.put("interview", interviewDto);
		model.put("interviewNames",interviewNameService.findAllForInterview(interviewDto));
		List<ApplicantDto> applicants = interviewDto.getId() == null ? applicantService.getAllAvailableForNewInterview() : List.of(interviewDto.getApplicant());
		model.put("applicants", applicants);
		String title = interviewDto.getId() == null ? "Create Interview" : "Edit Interview";
		model.put("title", title);
		model.put("contextPage", "/applicant/search");
		
		return "edit-interview";
	}
	
	@GetMapping("/interview/requirePositioon/edit")
	public String editInterviewFromRequirePosition (
			@RequestParam(required = false)
			Long applicantId,
			ModelMap model
			)  {
		InterviewDto interviewDto = interviewService.generateNewWithCode();
		ApplicantDto applicantDto = applicantService.findById(applicantId).orElse(null);
		interviewDto.setApplicant(applicantDto);
		
		model.put("interview", interviewDto);
		model.put("interviewNames",interviewNameService.findAllForInterview(interviewDto));
		List<ApplicantDto> applicants = interviewDto.getId() == null ? applicantService.getAllAvailableForNewInterview() : List.of(interviewDto.getApplicant());
		model.put("applicants", applicants);
		String title = interviewDto.getId() == null ? "Create Interview" : "Edit Interview";
		model.put("title", title);
		model.put("contextPage", "/requirePosition/detail?id=" + applicantDto.getRequirePosition().getId());
		
		return "edit-interview";
	}
	
	@PostMapping("/interview/save")
	public String saveInterview(
			@ModelAttribute("interview")
			@Validated
			InterviewDto dto,
			BindingResult bs,
			String contextPage,
			ModelMap model,
			RedirectAttributes redirect) {
		
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
		
		return "redirect:%s".formatted(contextPage);
	}

	@GetMapping("/interview/detail")
	public String showInterviewDetail(
			Long id,
			ModelMap model
			) {
		model.put("interview", interviewService.findById(id).get());
		return "interview-detail";
	}
	
	@PostMapping("/interview/delete")
	public String deleteInterview(@RequestParam("id") Long id,RedirectAttributes redirect) {
		interviewService.deleteById(id);
		redirect.addFlashAttribute("alert",new Alert("Successfully delete the interview!","notice-success"));
		return "redirect:/interview/search";
	}
	
	@PostMapping("/interview/status/save")
	public String statuschange(
			@RequestParam("id")Long id,
			@RequestParam("status")Status status,
			@RequestParam("comment")String comment,
			@RequestParam(name = "applicantStatusCheck", defaultValue = "false") boolean applicantStatusCheck,
			@RequestParam("applicantStatus") team.ojt7.recruitment.model.entity.Applicant.Status applicantStatus,
			@RequestParam("applicantStatusComment") String applicantStatusComment,
			RedirectAttributes redirect) {
		interviewService.saveInterviewStatus(id,status,comment,applicantStatusCheck, applicantStatus, applicantStatusComment);
		redirect.addFlashAttribute("alert",new Alert("Successfully changed the interview's status!","notice-success"));
		return "redirect:/interview/search";
		
	}
	
	@PostMapping("/interview/detail/status/save")
	public String statuschangeFromDetail(
			@RequestParam("id")Long id,
			@RequestParam("status")Status status,
			@RequestParam("comment")String comment,
			@RequestParam(name = "applicantStatusCheck", defaultValue = "false") boolean applicantStatusCheck,
			@RequestParam("applicantStatus") team.ojt7.recruitment.model.entity.Applicant.Status applicantStatus,
			@RequestParam("applicantStatusComment") String applicantStatusComment,
			RedirectAttributes redirect) {
		interviewService.saveInterviewStatus(id,status,comment,applicantStatusCheck, applicantStatus, applicantStatusComment);
		redirect.addFlashAttribute("alert",new Alert("Successfully changed the interview's status!","notice-success"));
		return "redirect:/interview/detail?id=" + id;
		
	}
	
	@GetMapping(value = "/interview/status/change/api")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> changeApplicantStatusFromRequirePositionDetailApi (
			@RequestParam
			Long id
			) {
		InterviewDto dto = interviewService.getCurrentStatus(id);
		Map<String, Object> map = new HashMap<>();
		map.put("id", dto.getId());
		map.put("status", dto.getStatus());
		map.put("applicantStatus", dto.getApplicant().getStatus());
		return ResponseEntity.ok(map);
	}
	
	
}

package team.ojt7.recruitment.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewNameSearch;
import team.ojt7.recruitment.model.service.InterviewNameService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class InterviewNameController {
	
	@Autowired
	InterviewNameService interviewService;
	
	@GetMapping("/interviewname/search")
	public String searchInterviews(
			@ModelAttribute("interview")
			InterviewNameSearch interview,
			ModelMap model) {
		Page<InterviewNameDto> list=interviewService.search(interview);
		model.put("list",list);
		model.put("interview", interview);
		return "interview-names";
	}

	@GetMapping("/interviewname/edit")
	public String editInterview(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		InterviewNameDto dto=interviewService.findById(id).orElse(new InterviewNameDto());
		model.put("interviewName", dto);
		String title=dto.getId()==null ? "Add Interview Title":"Edit Interview Title";
		model.put("title", title);
		return "interview-names";
	}
	@GetMapping("/interviewname/data")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDataFormTeamEdit(
			@RequestParam(required = false)
			Long id
			) {
		Map<String, Object> data = new HashMap<>();
		InterviewNameDto interviewNameDto = interviewService.findById(id).orElse(new InterviewNameDto());
		data.put("interviewName", interviewNameDto);
		String title = interviewNameDto.getId() == null ? "Add Interview Name" : "Edit Interview Name";
		data.put("title", title);
		return ResponseEntity.ok(data);
	}
	@PostMapping("/interviewname/validate")
	@ResponseBody
	public ResponseEntity<Map<String, String>> validateTeam(
			@Validated
			@ModelAttribute("interviewName")
			InterviewNameDto interviewNameDto,
			BindingResult bs
			) {
		Map<String, String> validation = new HashMap<>();
		
		try {
			interviewService.checkValidation(interviewNameDto);
		} catch (InvalidFieldsException e) {
			for (InvalidField invalidField : e.getFields()) {
				bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
			}
		}
		
		if (bs.hasErrors()) {
			for (FieldError error : bs.getFieldErrors()) {
				validation.put(error.getField(), error.getDefaultMessage());
			}
		}
		
		return ResponseEntity.ok(validation);
	}

	@PostMapping("/interviewname/save")
	public String saveInterview(@ModelAttribute("interviewName")
	@Validated
	InterviewNameDto dto,
	BindingResult bs,
	RedirectAttributes redirect,
	ModelMap model) {
		if (!bs.hasErrors()) {
			try {
				interviewService.save(InterviewNameDto.parse(dto));
				String message = dto.getId() == null ? "Successfully created a new InterviewName." : "Successfully updated the InterviewName.";
				String cssClass = dto.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidFiled : e.getFields()) {
					bs.rejectValue(invalidFiled.getField(), invalidFiled.getCode(), invalidFiled.getMessage());
				}
			}
		}
		
		if (bs.hasErrors()) {
			
			String title = dto.getId() == null ? "Add Interview Title" : "Edit Interview Title";
			model.put("title", title);
			return "interview-names";
		}
		return "redirect:/interviewname/search";
	}

	@PostMapping("/interviewname/delete")
	public String deleteInterview(@RequestParam("id")Long id) {
		interviewService.deleteById(id);
		
		return "redirect:/interviewname/search";
	}
	
	

}

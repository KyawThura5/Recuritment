package team.ojt7.recruitment.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.format.Formatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.dto.ApplicantStatusChangeDto;
import team.ojt7.recruitment.model.dto.ApplicantStatusChangeHistoryDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.ApplicantStatusChangeHistoryService;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;
import team.ojt7.recruitment.model.service.RequiredPositionService;
import team.ojt7.recruitment.model.service.VacancyService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
@MultipartConfig(maxFileSize = 5000000)
public class ApplicantController {
	
	@Autowired
	private ApplicantService applicantService;
		
	@Autowired
	private RequiredPositionService requriedPositionService;
	
	@Autowired
	private VacancyService vacancyService;
	
	@Autowired
	private ApplicantStatusChangeHistoryService applicantStatusChangeHistoryService;
		
	@Autowired
	private Formatter<RecruitmentResourceDto> recruitmentResourceDtoFormatter;
	
	@Autowired
	private Formatter<RequirePositionDto> requirePositionDtoFormatter;
	
	@Autowired
	private Formatter<VacancyDto> vacancyDtoFromatter;
	
	@Autowired
	@Qualifier("RecruitmentResource")
	private RecruitmentResourceService recruitmentResourceService;
	
	@Autowired
	private HttpSession session;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(recruitmentResourceDtoFormatter);
		binder.addCustomFormatter(requirePositionDtoFormatter);
		binder.addCustomFormatter(vacancyDtoFromatter);
	}

	@RequestMapping(value = "/applicant/edit", method = RequestMethod.GET)
			public String editApplicant(@RequestParam(required = false)
			Long id,
			ModelMap model) {
		ApplicantDto applicantDto = applicantService.findById(id).orElse(applicantService.generateNewWithCode());
		List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
		if (applicantDto.getVacancy() != null) {
			model.put("requirePositions", applicantDto.getVacancy().getRequirePositions());
		}
		
		String contextPage = "/applicant/search";
		
		model.put("vacancy",vacancyList);
		model.put("applicant", applicantDto);
		model.put("contextPage", contextPage);
		model.put("recruitmentResources", recruitmentResourceService.findAllForApplicant(applicantDto));
		return "edit-applicant";
	}
	
	@GetMapping("/applicant/vacancy/add")
	public String addNewApplicantForRequirePosition(
			@RequestParam
			Long id,
			ModelMap model
			) {
		ApplicantDto applicantDto = applicantService.generateNewWithCode();
		List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
		RequirePositionDto requirePositionDto = requriedPositionService.findById(id).orElse(null);
		applicantDto.setRequirePosition(requirePositionDto);
		applicantDto.setVacancy(requirePositionDto.getVacancy());
		
		String contextPage = "/vacancy/search";
		
		model.put("vacancy",vacancyList);
		model.put("requirePositions", requirePositionDto.getVacancy().getRequirePositions());
		model.put("applicant", applicantDto);
		model.put("contextPage", contextPage);
		model.put("recruitmentResources", recruitmentResourceService.findAllForApplicant(applicantDto));
		return "edit-applicant";
	}
	
	@RequestMapping(value = "/applicant/requirePosition/detail/edit", method = RequestMethod.GET)
	public String editApplicantFromRequirePositionDetail (@RequestParam(required = false)
			Long id,
			Long requirePositionId,
			ModelMap model) {
		RequirePositionDto requirePositionDto = requriedPositionService.findById(requirePositionId).orElse(null);
		ApplicantDto newApplicantDto = applicantService.generateNewWithCode();
		newApplicantDto.setVacancy(requirePositionDto.getVacancy());
		newApplicantDto.setRequirePosition(requirePositionDto);
		
		ApplicantDto applicantDto = applicantService.findById(id).orElse(newApplicantDto);
		List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
		
		
		if (applicantDto.getVacancy() != null) {
			model.put("requirePositions", applicantDto.getVacancy().getRequirePositions());
		}
		
		String contextPage = "/requirePosition/detail?id=%d".formatted(requirePositionId);
		
		model.put("vacancy",vacancyList);
		model.put("applicant", applicantDto);
		model.put("contextPage", contextPage);
		model.put("recruitmentResources", recruitmentResourceService.findAllForApplicant(applicantDto));
		return "edit-applicant";
	}
	

	@RequestMapping(value="/applicant/save",method=RequestMethod.POST)
	public String saveApplicant(
			@Validated
			@ModelAttribute("applicant")
			ApplicantDto applicantDto,
			BindingResult bindingResult,
			@RequestParam(required = false)
			CommonsMultipartFile attachedFile,
			@RequestParam
			String contextPage,
			RedirectAttributes redirect,
			ModelMap model) {
		
		if (!bindingResult.hasErrors()) {
			try {
				Applicant applicant = ApplicantDto.parse(applicantDto);
				applicantService.save(applicant, attachedFile);
				String message = applicantDto.getId() == null ? "Successfully created a new applicant." : "Successfully updated the applicant.";
				String cssClass = applicantDto.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
			if (applicantDto.getVacancy() != null) {
				model.put("requirePositions", applicantDto.getVacancy().getRequirePositions());
			}
			model.put("vacancy",vacancyList);
			model.put("recruitmentResources", recruitmentResourceService.findAllForApplicant(applicantDto));
			return "edit-applicant";
		}
		
		return "redirect:%s".formatted(contextPage);
		
	}

	@RequestMapping(value = "/applicant/delete", method = RequestMethod.POST)
	public String deleteApplicant(
			@RequestParam("id")
			Long id,
			RedirectAttributes redirect) {
		applicantService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the applicant.", "notice-success"));
		return "redirect:/applicant/search";
	}
	
	@GetMapping("/api/applicant/cv/download")
	@ResponseBody
	public void downloadCv(
			@RequestParam
			String url,
			HttpServletResponse response,
			HttpSession session
			) {
		try {
			
			String serverPath = session.getServletContext().getRealPath("/");
			File file = new File(serverPath + java.io.File.separator + url);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/applicant/search", method = RequestMethod.GET)
	public String searchApplicant(@ModelAttribute("applicantSearch")
	ApplicantSearch applicantSearch,
	ModelMap model) {
		User loginUser = (User) session.getAttribute("loginUser");
		Page<ApplicantDto> applicantPage = applicantService.search(applicantSearch);
		model.put("applicantSearch", applicantSearch);
		model.put("applicantPage", applicantPage);
		model.put("role", loginUser.getRole().name());
		return "applicants";
	}
	
	@GetMapping("/applicant/detail")
	public String showApplicantDetail(
			Long id,
			ModelMap model
			) {
		User loginUser = (User) session.getAttribute("loginUser");
		ApplicantDto applicant = applicantService.findById(id).orElse(null);
		List<ApplicantStatusChangeHistoryDto> aschList = applicantStatusChangeHistoryService.findAllByApplicantId(id);
		String contextPage = "/applicant/search";
		model.put("applicant", applicant);
		model.put("contextPage", contextPage);
		model.put("statusChangeHistories", aschList);
		model.put("role", loginUser.getRole().name());
		return "applicant-detail";
	}
	
	@GetMapping("/applicant/requirePosition/detail")
	public String showApplicantDetailFromRequirePositionDetail (
			Long id,
			Long requirePositionId,
			ModelMap model
			) {
		User loginUser = (User) session.getAttribute("loginUser");
		ApplicantDto applicant = applicantService.findById(id).orElse(null);
		List<ApplicantStatusChangeHistoryDto> aschList = applicantStatusChangeHistoryService.findAllByApplicantId(id);
		String contextPage = "/requirePosition/detail?id=" + requirePositionId;
		model.put("applicant", applicant);
		model.put("contextPage", contextPage);
		model.put("statusChangeHistories", aschList);
		model.put("role", loginUser.getRole().name());
		return "applicant-detail";
	}
	
	@GetMapping(value = "/applicant/requirePositionDetail/status/change/api")
	@ResponseBody
	public ResponseEntity<ApplicantStatusChangeDto> changeApplicantStatusFromRequirePositionDetailApi (
			@RequestParam
			Long id
			) {
		ApplicantStatusChangeDto aschDto = applicantStatusChangeHistoryService.getCurrentStatus(id);
		return ResponseEntity.ok(aschDto);
	}
	
	@PostMapping("/applicant/status/save")
	public String saveApplicateStatus(
			@ModelAttribute("statusChangeHistory")
			ApplicantStatusChangeHistoryDto statusChangeHistory,
			@RequestParam
			String contextPage,
			RedirectAttributes redirect
			) {
		applicantStatusChangeHistoryService.save(statusChangeHistory);
		redirect.addFlashAttribute("alert", new Alert("Successfully changed the applicant's status.", "notice-success"));
		return "redirect:%s".formatted(contextPage);
	}
	
	@PostMapping("/applicant/joinDate/save")
	public String saveApplicantJoinDate(
			Long id,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			LocalDate date,
			RedirectAttributes redirect
			) {
		applicantService.saveJoinDate(id, date);
		redirect.addFlashAttribute("alert", new Alert("Successfully updated candidate's join date.", "notice-info"));
		return "redirect:/applicant/detail?id=" + id;
	}

}
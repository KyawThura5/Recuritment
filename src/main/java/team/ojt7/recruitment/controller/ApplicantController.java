package team.ojt7.recruitment.controller;


import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.dto.ApplicantStatusChangeHistoryDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Applicant;
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(recruitmentResourceDtoFormatter);
		binder.addCustomFormatter(requirePositionDtoFormatter);
		binder.addCustomFormatter(vacancyDtoFromatter);
	}

	@RequestMapping(value = "/applicant/edit", method = RequestMethod.GET)
			public String addNewApplicant(@RequestParam(required = false)
			Long id,
			ModelMap model) {
		ApplicantDto applicantDto = applicantService.findById(id).orElse(applicantService.generateNewWithCode());
		List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
		if (applicantDto.getVacancy() != null) {
			model.put("requirePositions", applicantDto.getVacancy().getRequirePositions());
		}
		model.put("vacancy",vacancyList);
		model.put("applicant", applicantDto);
		model.put("recruitmentResources", recruitmentResourceService.findAllForApplicant(applicantDto));
		return "edit-applicant";
	}
	
	@GetMapping("/applicant/vacancy/add")
	public String addNewApplicantForRequirePosition(
			@RequestParam
			Long id,
			@RequestParam
			Long vacancyId,
			ModelMap model
			) {
		ApplicantDto applicantDto = applicantService.generateNewWithCode();
		List<VacancyDto> vacancyList =vacancyService.findAllForApplicant(applicantDto);
		RequirePositionDto requirePositionDto = requriedPositionService.findById(id).orElse(null);
		VacancyDto vacancy = vacancyService.findById(vacancyId).get();
		applicantDto.setRequirePosition(requirePositionDto);
		applicantDto.setVacancy(vacancy);
		model.put("vacancy",vacancyList);
		model.put("requirePositions", vacancy.getRequirePositions());
		model.put("applicant", applicantDto);
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
			ModelMap model) {
		
		if (!bindingResult.hasErrors()) {
			try {
				Applicant applicant = ApplicantDto.parse(applicantDto);
				applicantService.save(applicant, attachedFile);
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
		
		return "redirect:/applicant/search";
		
	}

	@RequestMapping(value = "/applicant/delete", method = RequestMethod.POST)
	public String deleteApplicant(@RequestParam("id")Long id) {
		applicantService.deleteById(id);
		return "redirect:/applicant/search";
	}

	@RequestMapping(value = "/applicant/search", method = RequestMethod.GET)
	public String searchApplicant(@ModelAttribute("applicantSearch")
	ApplicantSearch applicantSearch,
	ModelMap model) {
		Page<ApplicantDto> applicantPage = applicantService.search(applicantSearch);
		model.put("applicantSearch", applicantSearch);
		model.put("applicantPage", applicantPage);
		return "applicants";
	}
	
	@GetMapping("/applicant/detail")
	public String showApplicantDetail(
			Long id,
			ModelMap model
			) {
		ApplicantDto applicant = applicantService.findById(id).orElse(null);
		model.put("applicant", applicant);
		return "applicant-detail";
	}
	
	@GetMapping("/applicant/status/change")
	public String changeApplicantStatus(
			Long id,
			ModelMap model
			) {
		ApplicantStatusChangeHistoryDto aschDto = applicantStatusChangeHistoryService.getCurrentStatus(id);
		List<ApplicantStatusChangeHistoryDto> aschList = applicantStatusChangeHistoryService.findAllByApplicantId(id);
		model.put("statusChangeHistory", aschDto);
		model.put("statusChangeHistories", aschList);
		return "change-applicant-status";
	}
	
	@PostMapping("/applicant/status/save")
	public String saveApplicateStatus(
			@ModelAttribute("statusChangeHistory")
			ApplicantStatusChangeHistoryDto statusChangeHistory
			) {
		applicantStatusChangeHistoryService.save(statusChangeHistory);
		return "redirect:/applicant/detail?id=" + statusChangeHistory.getApplicantId();
	}

}
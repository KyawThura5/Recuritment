package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.DirectRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceSearch;
import team.ojt7.recruitment.model.service.DirectRecruitmentResourceService;
import team.ojt7.recruitment.model.service.ExternalRecruitmentResourceService;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class RecruitmentResourceController {
	
	@Autowired
	@Qualifier("RecruitmentResource")
	private RecruitmentResourceService recruitmentResourceService;
	
	@Autowired
	@Qualifier("direct")
	private DirectRecruitmentResourceService directRecruitmentResourceService;
	
	@Autowired
	@Qualifier("external")
	private ExternalRecruitmentResourceService externalRecruitmentResourceService;

	@GetMapping("/recruitmentresource/external/search")
	public String searchExternalRecruitmentResources(
			@ModelAttribute
			RecruitmentResourceSearch recruitmentResourceSearch,
			ModelMap model) {
		recruitmentResourceSearch.setEntityType("ExternalRecruitmentResource");
		Page<RecruitmentResourceDto> page = recruitmentResourceService.search(recruitmentResourceSearch);
		model.put("recruitmentResourcePage", page);
		model.put("title", "External Recruitment Resources");
		return "external-recruitment-resources";
	}
	
	@GetMapping("/recruitmentresource/direct/search")
	public String searchDirectRecruitmentResources(
			@ModelAttribute
			RecruitmentResourceSearch recruitmentResourceSearch,
			ModelMap model) {
		recruitmentResourceSearch.setEntityType("DirectRecruitmentResource");
		Page<RecruitmentResourceDto> page = recruitmentResourceService.search(recruitmentResourceSearch);
		model.put("recruitmentResourcePage", page);
		model.put("title", "Direct Recruitment Resources");
		return "direct-recruitment-resources";
	}
	
	@GetMapping("/recruitmentresource/external/edit")
	public String editExternalRecruitmentResource(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		ExternalRecruitmentResourceDto err = (ExternalRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(externalRecruitmentResourceService.generateNewWithCode());
		model.put("recruitmentResource", err);
		String title = err.getId() == null ? "Add New External Recruitment Resource" : "Edit External Recruitment Resource";
		model.put("title", title);
		return "edit-external-recruitment-resource";
	}
	
	@GetMapping("/recruitmentresource/direct/edit")
	public String editDirectRecruitmentResource(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		DirectRecruitmentResourceDto drr = (DirectRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(directRecruitmentResourceService.generateNewWithCode());
		model.put("recruitmentResource", drr);
		String title = drr.getId() == null ? "Add New Direct Recruitment Resource" : "Edit Direct Recruitment Resource";
		model.put("title", title);
		return "edit-direct-recruitment-resource";
	}
	
	@PostMapping("/recruitmentresource/external/save")
	public String saveExternalRecruitmentResource(
			@Validated
			@ModelAttribute("recruitmentResource")
			ExternalRecruitmentResourceDto err,
			BindingResult bindingResult,
			RedirectAttributes redirect,
			ModelMap model
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				recruitmentResourceService.save(RecruitmentResourceDto.parse(err));
				String message = err.getId() == null ? "Successfully created a new recruitment resource." : "Successfully updated the recruitment resource.";
				String cssClass = err.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			String title = err.getId() == null ? "Add New External Recruitment Resource" : "Edit External Recruitment Resource";
			model.put("title", title);
			return "edit-external-recruitment-resource";
		}
		
		return "redirect:/recruitmentresource/external/search";
	}
	
	@PostMapping("/recruitmentresource/direct/save")
	public String saveDirectRecruitmentResource(
			@Validated
			@ModelAttribute("recruitmentResource")
			DirectRecruitmentResourceDto drr,
			BindingResult bindingResult,
			RedirectAttributes redirect,
			ModelMap model
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				recruitmentResourceService.save(RecruitmentResourceDto.parse(drr));
				String message = drr.getId() == null ? "Successfully created a new recruitment resource." : "Successfully updated the recruitment resource.";
				String cssClass = drr.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			String title = drr.getId() == null ? "Add New Direct Recruitment Resource" : "Edit Direct Recruitment Resource";
			model.put("title", title);
			return "edit-direct-recruitment-resource";
		}
		
		return "redirect:/recruitmentresource/direct/search";
	}
	
	@GetMapping("/recruitmentresource/external/detail")
	public String showExternalRecruitmentResourceDetail(
			@RequestParam
			Long id,
			ModelMap model
			) {
		ExternalRecruitmentResourceDto errDto = (ExternalRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(null);
		model.put("recruitmentResource", errDto);
		model.put("title", "External Recruitment Resource Detail");
		return "external-recruitment-resource-detail";
	}
	
	@GetMapping("/recruitmentresource/direct/detail")
	public String showDirectRecruitmentResourceDetail(
			@RequestParam
			Long id,
			ModelMap model
			) {
		DirectRecruitmentResourceDto errDto = (DirectRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(null);
		model.put("recruitmentResource", errDto);
		model.put("title", "Direct Recruitment Resource Detail");
		return "direct-recruitment-resource-detail";
	}
	
	@PostMapping("/recruitmentresource/external/delete")
	public String deleteExternalRecruitmentResource(
			@RequestParam
			Long id,
			RedirectAttributes redirect) {
		recruitmentResourceService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the recruitment resource.", "notice-success"));
		return "redirect:/recruitmentresource/external/search";
	}
	
	@PostMapping("/recruitmentresource/direct/delete")
	public String deleteDirectRecruitmentResource(
			@RequestParam
			Long id,
			RedirectAttributes redirect) {
		recruitmentResourceService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the recruitment resource.", "notice-success"));
		return "redirect:/recruitmentresource/direct/search";
	}
}

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

import team.ojt7.recruitment.model.dto.DirectRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;
import team.ojt7.recruitment.model.service.exception.InvalidField;

@Controller
public class RecruitmentResourceController {
	
	@Autowired
	private RecruitmentResourceService recruitmentResourceService;

	@GetMapping("/manager/recruitment/external/search")
	public String searchExternalRecruitmentResources(
			@RequestParam(required = false)
			String keyword,
			ModelMap model) {
		List<RecruitmentResourceDto> recruitmentRecourses = recruitmentResourceService.search(keyword, "ExternalRecruitmentResource");
		model.put("recruitmentResourceList", recruitmentRecourses);
		model.put("title", "External Recruitment Resources");
		return "external-recruitment-resources";
	}
	
	@GetMapping("/manager/recruitment/direct/search")
	public String searchDirectRecruitmentResources(
			@RequestParam(required = false)
			String keyword,
			ModelMap model) {
		List<RecruitmentResourceDto> recruitmentRecourses = recruitmentResourceService.search(keyword, "DirectRecruitmentResource");
		model.put("recruitmentResourceList", recruitmentRecourses);
		model.put("title", "Direct Recruitment Resources");
		return "direct-recruitment-resources";
	}
	
	@GetMapping("/hr/recruitment/external/edit")
	public String editExternalRecruitmentResource(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		ExternalRecruitmentResourceDto err = (ExternalRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(new ExternalRecruitmentResourceDto());
		model.put("recruitmentResource", err);
		String title = err.getId() == null ? "Add New External Recruitment Resource" : "Edit External Recruitment Resource";
		model.put("title", title);
		return "edit-external-recruitment-resource";
	}
	
	@GetMapping("/hr/recruitment/direct/edit")
	public String editDirectRecruitmentResource(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		DirectRecruitmentResourceDto drr = (DirectRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(new DirectRecruitmentResourceDto());
		model.put("recruitmentResource", drr);
		String title = drr.getId() == null ? "Add New Direct Recruitment Resource" : "Edit Direct Recruitment Resource";
		model.put("title", title);
		return "edit-direct-recruitment-resource";
	}
	
	@PostMapping("/hr/recruitment/external/save")
	public String saveExternalRecruitmentResource(
			@Validated
			@ModelAttribute("recruitmentResource")
			ExternalRecruitmentResourceDto err,
			BindingResult bindingResult,
			ModelMap model
			) {
		
//		if (!bindingResult.hasErrors()) {
//			try {
//				recruitmentResourceService.save(RecruitmentResourceDto.parse(err));
//			} catch (InvalidField e) {
//				bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
//			}
//		}
		
		if (bindingResult.hasErrors()) {
			String title = err.getId() == null ? "Add New External Recruitment Resource" : "Edit External Recruitment Resource";
			model.put("title", title);
			return "edit-external-recruitment-resource";
		}
		
		return "redirect:/manager/recruitment/external/search";
	}
	
	@PostMapping("/hr/recruitment/direct/save")
	public String saveDirectRecruitmentResource(
			@Validated
			@ModelAttribute("recruitmentResource")
			DirectRecruitmentResourceDto drr,
			BindingResult bindingResult,
			ModelMap model
			) {
		
//		if (!bindingResult.hasErrors()) {
//			try {
//				recruitmentResourceService.save(RecruitmentResourceDto.parse(drr));
//			} catch (InvalidField e) {
//				bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
//			}
//		}
		
		if (bindingResult.hasErrors()) {
			String title = drr.getId() == null ? "Add New Direct Recruitment Resource" : "Edit Direct Recruitment Resource";
			model.put("title", title);
			return "edit-direct-recruitment-resource";
		}
		
		return "redirect:/manager/recruitment/direct/search";
	}
	
	@GetMapping("/manager/recruitment/external/detail")
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
	
	@GetMapping("/manager/recruitment/direct/detail")
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
	
	@PostMapping("/hr/recruitment/external/delete")
	public String deleteExternalRecruitmentResource(@RequestParam Long id) {
		recruitmentResourceService.deleteById(id);
		return "redirect:/manager/recruitment/external/search";
	}
	
	@PostMapping("/hr/recruitment/direct/delete")
	public String deleteDirectRecruitmentResource(@RequestParam Long id) {
		recruitmentResourceService.deleteById(id);
		return "redirect:/manager/recruitment/direct/search";
	}
}

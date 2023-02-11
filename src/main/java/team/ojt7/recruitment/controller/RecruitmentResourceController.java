package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.entity.ExternalRecruitmentResource;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;

@Controller
public class RecruitmentResourceController {
	
	@Autowired
	private RecruitmentResourceService recruitmentResourceService;

	@GetMapping("/manager/recruitment/external/search")
	public String searchExternalRecruitmentResources(
			@RequestParam(required = false)
			String keyword,
			ModelMap model) {
		List<RecruitmentResourceDto> recruitmentRecourses = recruitmentResourceService.search(keyword);
		model.put("recruitmentResourceList", recruitmentRecourses);
		return "external-recruitments";
	}
	
	@GetMapping("/hr/recruitment/external/edit")
	public String editExternalRecruitmentResource(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		ExternalRecruitmentResourceDto err = (ExternalRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(new ExternalRecruitmentResourceDto());
		model.put("recruitmentResource", err);
		return "edit-external-recruitment-resource";
	}
	
	@PostMapping("/hr/recruitment/external/save")
	public String saveExternalRecruitmentResource(
			@ModelAttribute("recruitmentResource")
			ExternalRecruitmentResource err
			) {
		recruitmentResourceService.save(err);
		return "redirect:/manager/recruitment/external/search";
	}
	
	@GetMapping("/manager/recruitment/external/detail")
	public String showExternalRecruitmentResourceDetail(
			@RequestParam
			Long id,
			ModelMap model
			) {
		ExternalRecruitmentResourceDto errDto = (ExternalRecruitmentResourceDto) recruitmentResourceService.findById(id).orElse(null);
		model.put("recruitmentResource", errDto);
		return "external-recruitment-resource-detail";
	}
	
	@PostMapping("/hr/recruitment/external/delete")
	public String deleteExternalRecruitmentResource(@RequestParam Long id) {
		recruitmentResourceService.deleteById(id);
		return "redirect:/manager/recruitment/external/search";
	}
}

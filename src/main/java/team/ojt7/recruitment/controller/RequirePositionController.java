package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import team.ojt7.recruitment.model.dto.RequirePositionDetailSearch;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.service.RequiredPositionService;

@Controller
public class RequirePositionController {
	
	@Autowired
	private RequiredPositionService requiredPositionService;

	@GetMapping("/requirePosition/detail")
	public String showDetail(
			@ModelAttribute
			RequirePositionDetailSearch requirePositionDetailSearch,
			ModelMap model
			) {
		RequirePositionDto requiePositionDto = requiredPositionService.findDetail(requirePositionDetailSearch).orElse(null);
		model.put("requirePosition", requiePositionDto);
		return "require-position-detail";
	}
}

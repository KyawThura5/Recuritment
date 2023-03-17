package team.ojt7.recruitment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import team.ojt7.recruitment.model.dto.RequirePositionDetailSearch;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.RequiredPositionService;

@Controller
public class RequirePositionController {
	
	@Autowired
	private RequiredPositionService requiredPositionService;

	@Autowired
	private HttpSession session;

	@GetMapping("/requirePosition/detail")
	public String showDetail(
			@ModelAttribute
			RequirePositionDetailSearch requirePositionDetailSearch,
			ModelMap model
			) {
		User loginUser = (User) session.getAttribute("loginUser");
		RequirePositionDto primaryRequirePositionDto = requiredPositionService.findById(requirePositionDetailSearch.getId()).orElse(null);
		RequirePositionDto requiePositionDto = requiredPositionService.findDetail(requirePositionDetailSearch).orElse(null);
		model.put("requirePosition", primaryRequirePositionDto);
		model.put("applicants", requiePositionDto.getActiveApplicants());
		model.put("role", loginUser.getRole().name());
		return "require-position-detail";
	}
}

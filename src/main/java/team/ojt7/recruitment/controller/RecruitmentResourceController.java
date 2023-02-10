package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecruitmentResourceController {

	@GetMapping("/manager/recruitment/search/external")
	public String searchExternalRecruitments() {
		return "external-recruitments";
	}
}

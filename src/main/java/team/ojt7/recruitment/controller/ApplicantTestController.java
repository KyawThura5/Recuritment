package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicantTestController {

	@GetMapping("/manager/test/applicant/search")
	public String searchApplicants() {
		return "applicants";
	}
}

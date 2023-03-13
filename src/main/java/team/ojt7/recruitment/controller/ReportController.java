package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {

	@GetMapping("/report/{name}")
	public String showReport(
			@PathVariable
			String name
			) {
		if ("topRecruitmentResources".equals(name)) {
			return "top-recruitment-resources";
		} else if ("topRecruitmentResourcesByPosition".equals(name)) {
			return "top-recruitment-resources-by-position";
		}
		return null;
	}
}

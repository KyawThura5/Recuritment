package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.service.ReportService;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService reportService;

	@GetMapping("/report")
	public String showReport(
			@RequestParam
			String name,
			ModelMap model
			) {
		if ("topRecruitmentResources".equals(name)) {
			model.put("reports", reportService.searchTopRecruitmentResources());
			return "top-recruitment-resources";
		} else if ("topRecruitmentResourcesByPosition".equals(name)) {
			model.put("reports", reportService.searchTopRecruitmentResourcesByPosition());
			return "top-recruitment-resources-by-position";
		}
		return null;
	}
}

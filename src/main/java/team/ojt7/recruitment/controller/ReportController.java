package team.ojt7.recruitment.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateFrom,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateTo,
			ModelMap model
			) {
		if ("topRecruitmentResources".equals(name)) {
			model.put("reports", reportService.searchTopRecruitmentResources());
			return "top-recruitment-resources";
		} else if ("topRecruitmentResourcesByPosition".equals(name)) {
			model.put("reports", reportService.searchTopRecruitmentResourcesByPosition());
			return "top-recruitment-resources-by-position";
		} else if ("demandPositions".equals(name)) {
			model.put("reports", reportService.searchDemandPositionReport(dateFrom, dateTo));
			return "demand-positions-report";
		}
		return null;
	}
}

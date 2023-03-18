package team.ojt7.recruitment.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;
import team.ojt7.recruitment.model.service.ReportService;
import team.ojt7.recruitment.security.util.JasperExporter;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private JasperExporter jasperExporter;

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
	
	@GetMapping("/report/export")
	public String exportReport(
			@RequestParam
			String name,
			HttpServletRequest req,
			HttpServletResponse resp,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateFrom,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateTo
			) {
		if ("topRecruitmentResources".equals(name)) {
			List<TopRecruitmentResourceReportDto> report = reportService.searchTopRecruitmentResources();
			report.add(0, null);
			try {
				var jasperPath = ResourceUtils.getFile("classpath:jasper/Report1.jrxml").getAbsolutePath();
			
				var fileName = "top-recruitment-resources-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType("pdf"));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, "pdf"));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				
				var jrDataSource = new JRBeanCollectionDataSource(report, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, "pdf", resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		} else if ("topRecruitmentResourcesByPosition".equals(name)) {
//			model.put("reports", reportService.searchTopRecruitmentResourcesByPosition());
			return "top-recruitment-resources-by-position";
		} else if ("demandPositions".equals(name)) {
			var report = reportService.searchDemandPositionReport(dateFrom, dateTo);
			report.entrySet();
			var list = new LinkedList<>(report.entrySet());
			list.add(0, null);
			try {
				var jasperPath = ResourceUtils.getFile("classpath:jasper/Report3.jrxml").getAbsolutePath();
			
				var fileName = "top-recruitment-resources-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType("pdf"));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, "pdf"));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				param.put("dateFrom",dateFrom);
				param.put("dateTo",dateTo);
				var jrDataSource = new JRBeanCollectionDataSource(list, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, "pdf", resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

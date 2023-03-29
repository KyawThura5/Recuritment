package team.ojt7.recruitment.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewStageInfoDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
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
			@RequestParam(required = false)
			String sort,
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
		} else if ("interviewStages".equals(name)) {
			model.put("reports", reportService.searchInterviewStageInfoReport(dateFrom, dateTo, sort));
			return "interview-stages-report";
		}
		return null;
	}
	
	@GetMapping("/report/export")
	public String exportReport(
			@RequestParam
			String name,
			@RequestParam
			String extension,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateFrom,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(required = false)
			LocalDate dateTo,
			@RequestParam(required = false)
			String sort,
			HttpServletRequest req,
			HttpServletResponse resp
			) {
		if ("topRecruitmentResources".equals(name)) {
			List<TopRecruitmentResourceReportDto> report = reportService.searchTopRecruitmentResources();
			report.add(0, null);
			try {
				String jrxmlFile = "pdf".equals(extension) ? "classpath:jasper/Report1.jrxml" : "xls".equals(extension) ? "classpath:jasper/Report1x.jrxml" : null;
				var jasperPath = ResourceUtils.getFile(jrxmlFile).getAbsolutePath();
			
				var fileName = "top-recruitment-resources-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType(extension));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				
				var jrDataSource = new JRBeanCollectionDataSource(report, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, extension, resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		} else if ("topRecruitmentResourcesByPosition".equals(name)) {
			List<TopRecruitmentResourceByPositionDto> report = reportService.searchTopRecruitmentResourcesByPosition();
			report.add(0, null);
			try {
				String jrxmlFile = "pdf".equals(extension) ? "classpath:jasper/Report2.jrxml" : "xls".equals(extension) ? "classpath:jasper/Report2x.jrxml" : null;
				var jasperPath = ResourceUtils.getFile(jrxmlFile).getAbsolutePath();
			
				var fileName = "top-demand-positions-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType(extension));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				
				var jrDataSource = new JRBeanCollectionDataSource(report, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, extension, resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		} else if ("demandPositions".equals(name)) {
			Map<PositionDto, CandidateCountInfo> report = reportService.searchDemandPositionReport(dateFrom, dateTo);
			
			try {
				String jrxmlFile = "pdf".equals(extension) ? "classpath:jasper/Report3.jrxml" : "xls".equals(extension) ? "classpath:jasper/Report3x.jrxml" : null;
				var jasperPath = ResourceUtils.getFile(jrxmlFile).getAbsolutePath();
			
				var fileName = "position-fullfillment-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType(extension));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				param.put("dateFrom", dateFrom);
				param.put("dateTo", dateTo);
				List<Entry<PositionDto, CandidateCountInfo>> reportList = new ArrayList<>(report.entrySet());
				reportList.add(0, null);
				var jrDataSource = new JRBeanCollectionDataSource(reportList, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, extension, resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		} else if ("interviewStages".equals(name)) {
			Map<InterviewNameDto, InterviewStageInfoDto> report = reportService.searchInterviewStageInfoReport(dateFrom, dateTo, sort);
			
			try {
				String jrxmlFile = "pdf".equals(extension) ? "classpath:jasper/Report4.jrxml" : "xls".equals(extension) ? "classpath:jasper/Report4x.jrxml" : null;
				var jasperPath = ResourceUtils.getFile(jrxmlFile).getAbsolutePath();
			
				var fileName = "interview-stages-report-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
				
				resp.setContentType(JasperExporter.getContentType(extension));
				resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
				
				var param = new HashMap<String, Object>();
				param.put("title", "Top Recruitment Resources");
				param.put("dateFrom", dateFrom);
				param.put("dateTo", dateTo);
				List<Entry<InterviewNameDto, InterviewStageInfoDto>> reportList = new ArrayList<>(report.entrySet());
				reportList.add(0, null);
				var jrDataSource = new JRBeanCollectionDataSource(reportList, false);
				param.put("Report", jrDataSource);
				var reportData = JasperCompileManager.compileReport(jasperPath);
				var print = JasperFillManager.fillReport(reportData, param, jrDataSource);
				jasperExporter.export(print, extension, resp.getOutputStream());
			} catch (IOException | JRException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

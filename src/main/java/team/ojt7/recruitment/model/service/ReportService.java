package team.ojt7.recruitment.model.service;

import java.time.LocalDate;
import java.util.List;

import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;

public interface ReportService {

	List<TopRecruitmentResourceReportDto> searchTopRecruitmentResources(LocalDate dateFrom, LocalDate dateTo);
}

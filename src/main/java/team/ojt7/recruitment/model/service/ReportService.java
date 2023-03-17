package team.ojt7.recruitment.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;

public interface ReportService {

	List<TopRecruitmentResourceReportDto> searchTopRecruitmentResources();
	
	List<TopRecruitmentResourceByPositionDto> searchTopRecruitmentResourcesByPosition();
	
	Map<PositionDto, CandidateCountInfo> searchDemandPositionReport(LocalDate dateFrom, LocalDate dateTo);
}

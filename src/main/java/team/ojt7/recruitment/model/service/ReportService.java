package team.ojt7.recruitment.model.service;

import java.util.List;

import team.ojt7.recruitment.model.dto.TopRecruitmentResourceByPositionDto;
import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;

public interface ReportService {

	List<TopRecruitmentResourceReportDto> searchTopRecruitmentResources();
	
	List<TopRecruitmentResourceByPositionDto> searchTopRecruitmentResourcesByPosition();
}

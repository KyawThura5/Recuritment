package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.TopRecruitmentResourceReportDto;
import team.ojt7.recruitment.model.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Override
	public List<TopRecruitmentResourceReportDto> searchTopRecruitmentResources(LocalDate dateFrom, LocalDate dateTo) {
		// TODO Auto-generated method stub
		return null;
	}

}

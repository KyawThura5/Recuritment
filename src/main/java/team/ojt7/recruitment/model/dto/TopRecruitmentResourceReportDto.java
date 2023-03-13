package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Map;

import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.RecruitmentResource;

public class TopRecruitmentResourceReportDto {

	private RecruitmentResourceDto recruitmentResource;
	private long count;
	private Map<PositionDto, Integer> positions;

	public RecruitmentResourceDto getRecruitmentResource() {
		return recruitmentResource;
	}

	public void setRecruitmentResource(RecruitmentResourceDto recruitmentResource) {
		this.recruitmentResource = recruitmentResource;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Map<PositionDto, Integer> getPositions() {
		return positions;
	}

	public void setPositions(Map<PositionDto, Integer> positions) {
		this.positions = positions;
	}

	

	
	
}

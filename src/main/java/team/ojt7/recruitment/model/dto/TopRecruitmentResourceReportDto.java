package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Map;

import team.ojt7.recruitment.model.entity.Position;

public class TopRecruitmentResourceReportDto {

	private RecruitmentResourceDto recruitmentResource;
	private long count;
	private List<Map<Position, Integer>> positions;

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

	public List<Map<Position, Integer>> getPositions() {
		return positions;
	}

	public void setPositions(List<Map<Position, Integer>> positions) {
		this.positions = positions;
	}

}

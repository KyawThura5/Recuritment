package team.ojt7.recruitment.model.dto;

import java.util.Map;

public class TopRecruitmentResourceByPositionDto {

	private PositionDto position;
	private Integer count;
	private Map<RecruitmentResourceDto, Integer> recruitmentResources;

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Map<RecruitmentResourceDto, Integer> getRecruitmentResources() {
		return recruitmentResources;
	}

	public void setRecruitmentResources(Map<RecruitmentResourceDto, Integer> recruitmentResources) {
		this.recruitmentResources = recruitmentResources;
	}

}

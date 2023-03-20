package team.ojt7.recruitment.model.dto;

import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	public JRBeanCollectionDataSource getPositionList() {
		return new JRBeanCollectionDataSource(positions.entrySet());
	}

	
	
}

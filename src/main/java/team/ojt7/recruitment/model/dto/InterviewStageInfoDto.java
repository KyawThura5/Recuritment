package team.ojt7.recruitment.model.dto;

import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class InterviewStageInfoDto {
	private Long total;
	private Long passed;
	private Long failed;
	private Map<PositionDto, InterviewStageInfoByPosition> stagesByPosition;

	public Long getTotal() {
		return total == null ? 0 : total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPassed() {
		return passed == null ? 0 : passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

	public Long getFailed() {
		return failed == null ? 0 : failed;
	}

	public void setFailed(Long failed) {
		this.failed = failed;
	}

	public Map<PositionDto, InterviewStageInfoByPosition> getStagesByPosition() {
		return stagesByPosition;
	}

	public void setStagesByPosition(Map<PositionDto, InterviewStageInfoByPosition> stagesByPosition) {
		this.stagesByPosition = stagesByPosition;
	}
	
	public JRBeanCollectionDataSource getStageInfoByPosition() {
		return new JRBeanCollectionDataSource(stagesByPosition.entrySet());
	}
	
	public int getPercentage() {
		return (int) ((getPassed() * 100) / getTotal());
	}

}

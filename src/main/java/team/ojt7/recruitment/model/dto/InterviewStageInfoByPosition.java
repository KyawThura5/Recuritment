package team.ojt7.recruitment.model.dto;

public class InterviewStageInfoByPosition {

	private Long total;
	private Long passed;
	private Long failed;

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

}

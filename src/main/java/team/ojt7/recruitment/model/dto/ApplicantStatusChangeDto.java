package team.ojt7.recruitment.model.dto;

import team.ojt7.recruitment.model.entity.Applicant.Status;

public class ApplicantStatusChangeDto {

	private Long applicantId;
	private Status status;
	private boolean updatable;

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}
	
	

}

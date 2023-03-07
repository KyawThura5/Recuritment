package team.ojt7.recruitment.model.dto;

import team.ojt7.recruitment.model.entity.Applicant.Status;

public class RequirePositionDetailSearch {

	private Long id;
	private String keyword;
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}

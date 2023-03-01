package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;

import team.ojt7.recruitment.model.entity.Interview.Status;

public class InterviewSearch {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	private String keyword;
	private LocalDate dateForm;
	private LocalDate dateTo;
	private Status status;
	private InterviewNameDto interviewName;
	
	private Integer page;
	private Integer size;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public LocalDate getDateForm() {
		return dateForm;
	}

	public void setDateForm(LocalDate dateForm) {
		this.dateForm = dateForm;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public InterviewNameDto getInterviewName() {
		return interviewName;
	}

	public void setInterviewName(InterviewNameDto interviewName) {
		this.interviewName = interviewName;
	}

	public Integer getPage() {
		return page == null ? DEFAULT_PAGE : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size == null ? DEFAULT_SIZE : size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	

}

package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Interview.Status;

public class InterviewSearch {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateTo;
	private Status status;
	private InterviewNameDto interviewName;
	
	private Integer page;
	private Integer size;
	
	private String sortBy = "interviewName";

	private String sortDirection = "asc";
	
	

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
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

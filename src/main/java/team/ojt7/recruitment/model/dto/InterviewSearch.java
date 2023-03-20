package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Interview.Status;

public class InterviewSearch {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 30;

	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateTo;
	private Status status;
	private InterviewNameDto interviewName;
	
	private Integer page;
	private Integer size;
	
	private Sort sort = Sort.LAST_MODIFIED;

	public enum Sort {
		NEWEST("Newest", org.springframework.data.domain.Sort.by("createdDateTime").descending()),
		OLDEST("Oldest", org.springframework.data.domain.Sort.by("createdDateTime").ascending()),
		FIRST_MODIFIED("First Modified", org.springframework.data.domain.Sort.by("updatedOn").ascending()),
		LAST_MODIFIED("Last Modified", org.springframework.data.domain.Sort.by("updatedOn").descending()),
		CODE_SORTING_A_TO_Z("Code A To Z",org.springframework.data.domain.Sort.by("code").ascending()),
		CODE_SORTING_Z_TO_A("Code Z To A",org.springframework.data.domain.Sort.by("code").descending()),
		NAME_SORTING_Z_TO_A("Name Z To A",org.springframework.data.domain.Sort.by("interviewName").descending()),
		NAME_SORTING_A_TO_Z("Name A To Z",org.springframework.data.domain.Sort.by("interviewName").ascending())
		;

		private String displayName;
		private org.springframework.data.domain.Sort sort;
		

		Sort(String displayName, org.springframework.data.domain.Sort sort) {
			this.displayName = displayName;
			this.sort = sort;
		}
		
		public org.springframework.data.domain.Sort getSort() {
			return sort;
		}

		public String getDisplayName() {
			return displayName;
		}

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

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
	
	

}

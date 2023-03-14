package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Applicant.Status;

public class ApplicantSearch {

	public static int DEFAULT_PAGE = 1;
	public static int DEFAULT_SIZE = 10;

	private String keyword;

	private Status status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateTo;
	private Integer page;
	private Integer size;
	private ApplicantDto name;
	private String sortBy = "name";

	private String sortDirection = "asc";
	
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
	public ApplicantDto getName() {
		return name;
	}

	public void setName(ApplicantDto name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateFrom, dateTo, keyword, page, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicantSearch other = (ApplicantSearch) obj;
		return Objects.equals(dateFrom, other.dateFrom) && Objects.equals(dateTo, other.dateTo)
				&& Objects.equals(keyword, other.keyword) && Objects.equals(page, other.page)
				&& Objects.equals(size, other.size);
	}

}

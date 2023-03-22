package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Vacancy.Status;

public class VacancySearch {

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

	private Sort sort = Sort.NEWEST;

	public enum Sort {
		NEWEST("Newest", org.springframework.data.domain.Sort.by("createdDateTime").descending()),
		OLDEST("Oldest", org.springframework.data.domain.Sort.by("createdDateTime").ascending()),
		FIRST_EXPIRING("First Expiring", org.springframework.data.domain.Sort.by("dueDate").ascending()),
		LAST_EXPIRING("Last Expiring", org.springframework.data.domain.Sort.by("dueDate").descending())
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

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateFrom, dateTo, keyword, page, size, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VacancySearch other = (VacancySearch) obj;
		return Objects.equals(dateFrom, other.dateFrom) && Objects.equals(dateTo, other.dateTo)
				&& Objects.equals(keyword, other.keyword) && Objects.equals(page, other.page)
				&& Objects.equals(size, other.size) && status == other.status;
	}

}

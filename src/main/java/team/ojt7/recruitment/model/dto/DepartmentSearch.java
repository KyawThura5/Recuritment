package team.ojt7.recruitment.model.dto;

import java.util.Objects;

public class DepartmentSearch {
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	private String keyword;
	private Integer page;
	private Integer size;
	
	private String sortBy = "name";

	private String sortDirection = "asc";

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	@Override
	public int hashCode() {
		return Objects.hash(keyword, page, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentSearch other = (DepartmentSearch) obj;
		return Objects.equals(keyword, other.keyword) && Objects.equals(page, other.page)
				&& Objects.equals(size, other.size);
	}

}

package team.ojt7.recruitment.model.dto;

import java.util.Objects;


public class DepartmentSearch {
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	private String keyword;
	private Integer page;
	private Integer size;
	
	private Sort sort = Sort.NAME_SORTING_A_TO_Z;

	public enum Sort {
		NAME_SORTING_Z_TO_A("Name ZtoA Sorting",org.springframework.data.domain.Sort.by("name").descending()),
		NAME_SORTING_A_TO_Z("Name AtoZ Sorting",org.springframework.data.domain.Sort.by("name").ascending())
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

package team.ojt7.recruitment.model.dto;

import java.util.Objects;

public class PositionSearch {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 20;

	private String keyword;
	private Integer page;
	private Integer size;
	
	private Sort sort = Sort.NAME_SORTING_A_TO_Z;

	public enum Sort {
		NAME_SORTING_A_TO_Z("Name A To Z",org.springframework.data.domain.Sort.by("name").ascending()),
		NAME_SORTING_Z_TO_A("Name Z To A",org.springframework.data.domain.Sort.by("name").descending())
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

	
	
	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
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
		PositionSearch other = (PositionSearch) obj;
		return Objects.equals(keyword, other.keyword) && Objects.equals(page, other.page)
				&& Objects.equals(size, other.size);
	}
}

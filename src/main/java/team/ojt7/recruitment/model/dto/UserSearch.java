package team.ojt7.recruitment.model.dto;

import java.util.Objects;

import team.ojt7.recruitment.model.entity.User.Role;

public class UserSearch {

	public static int DEFAULT_PAGE = 1;
	public static int DEFAULT_SIZE = 10;
	
	private String keyword;
	private Role role;
	private Integer page;
	private Integer size;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
		return Objects.hash(keyword, page, role, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSearch other = (UserSearch) obj;
		return Objects.equals(keyword, other.keyword) && Objects.equals(page, other.page) && role == other.role
				&& Objects.equals(size, other.size);
	}

	

}

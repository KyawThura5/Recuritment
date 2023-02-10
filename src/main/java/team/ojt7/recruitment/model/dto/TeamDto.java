package team.ojt7.recruitment.model.dto;

import javax.validation.constraints.NotEmpty;

import team.ojt7.recruitment.model.entity.Department;

public class TeamDto {
	@NotEmpty
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private Department departments;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartments() {
		return departments;
	}
	public void setDepartments(Department departments) {
		this.departments = departments;
	}
	
	

}

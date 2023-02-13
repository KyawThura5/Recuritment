package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import team.ojt7.recruitment.model.entity.Department;

public class DepartmentDto {

	private Long id;

	private String name;
	
	private boolean isDeleted;
	
	private List<TeamDto> teams;
	
	public static DepartmentDto of(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(department.getId());
		departmentDto.setName(department.getName());
		departmentDto.setDeleted(department.isDeleted());
		departmentDto.setTeams(TeamDto.ofList(department.getTeams()));
		return departmentDto;
	}
	
	public static List<DepartmentDto> ofList(List<Department> departments) {
		return departments.stream().map(d -> of(d)).toList();
	}
	
	public static Department parse(DepartmentDto departmentDto) {
		Department department = new Department();
		department.setId(departmentDto.getId());
		department.setName(departmentDto.getName());
		department.setDeleted(departmentDto.isDeleted());
		department.setTeams(TeamDto.parseList(departmentDto.getTeams()));
		return department;
	}

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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<TeamDto> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamDto> teams) {
		this.teams = teams;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isDeleted, name, teams);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentDto other = (DepartmentDto) obj;
		return Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name)
				&& Objects.equals(teams, other.teams);
	}
	
}
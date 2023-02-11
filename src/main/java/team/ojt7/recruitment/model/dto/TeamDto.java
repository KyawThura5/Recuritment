package team.ojt7.recruitment.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Team;

public class TeamDto {
	
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private DepartmentDto department;
	
	public static TeamDto of(Team team) {
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(team.getDepartment().getId());
		departmentDto.setName(team.getDepartment().getName());
		
		TeamDto teamDto = new TeamDto();
		teamDto.setId(team.getId());
		teamDto.setName(team.getName());
		teamDto.setDepartment(departmentDto);
		return teamDto;
	}
	
	public static List<TeamDto> ofList(List<Team> teams) {
		return teams.stream().map(t -> of(t)).toList();
	}
	
	public static Team parse(TeamDto teamDto) {
		Department department = new Department();
		department.setId(teamDto.getDepartment().getId());
		department.setName(teamDto.getDepartment().getName());
		
		Team team = new Team();
		team.setId(teamDto.getId());
		team.setName(teamDto.getName());
		team.setDepartment(department);
		return team;
	}
	
	public static List<Team> parseList(List<TeamDto> teamDtos) {
		return teamDtos.stream().map(t -> parse(t)).toList();
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

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamDto other = (TeamDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	

}
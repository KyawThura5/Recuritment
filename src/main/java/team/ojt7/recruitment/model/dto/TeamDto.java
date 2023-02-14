package team.ojt7.recruitment.model.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Team;

public class TeamDto {
	
	private Long id;
	
	@NotBlank(message="{NotBlank.team.name}")
	private String name;
	
	private boolean isDeleted;
	@NotNull(message="{NotNull.team.department}")
	private DepartmentDto department;
	
	public static TeamDto of(Team team) {
		if (team == null) {
			return null;
		}
		
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(team.getDepartment().getId());
		departmentDto.setName(team.getDepartment().getName());
		departmentDto.setDeleted(team.getDepartment().isDeleted());
		
		TeamDto teamDto = new TeamDto();
		teamDto.setId(team.getId());
		teamDto.setName(team.getName());
		teamDto.setDeleted(team.isDeleted());
		teamDto.setDepartment(departmentDto);
		return teamDto;
	}
	
	public static List<TeamDto> ofList(List<Team> teams) {
		if (teams == null) {
			return Collections.emptyList();
		}
		return teams.stream().map(t -> of(t)).toList();
	}
	
	public static Team parse(TeamDto teamDto) {
		if (teamDto == null) {
			return null;
		}
		
		Department department = new Department();
		department.setId(teamDto.getDepartment().getId());
		department.setName(teamDto.getDepartment().getName());
		department.setDeleted(teamDto.getDepartment().isDeleted());
		
		Team team = new Team();
		team.setId(teamDto.getId());
		team.setName(teamDto.getName());
		team.setDeleted(teamDto.isDeleted());
		team.setDepartment(department);
		return team;
	}
	
	public static List<Team> parseList(List<TeamDto> teamDtos) {
		if (teamDtos == null) {
			return Collections.emptyList();
		}
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
	
	

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isDeleted, name);
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
		return Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name);
	}
	

}
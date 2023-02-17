package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

public class VacancyDto {

	private Long id;
	private String code;
	private Team team;
	private LocalDate createdDate;
	private LocalDate dueDate;
	private Status status;
	private String comment;
	private boolean deleted;
	private List<RequirePositionDto> requirePositions;

	public static VacancyDto of(Vacancy vacancy) {
		return null;
	}

	public static List<VacancyDto> ofList(List<Vacancy> list) {
		return list.stream().map(d -> of(d)).toList();
	}

	public static Vacancy parse(VacancyDto dto) {
		return null;
	}

	public static List<Vacancy> parseList(List<VacancyDto> dtoList) {
		return dtoList.stream().map(d -> parse(d)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<RequirePositionDto> getRequirePositions() {
		return requirePositions;
	}

	public void setRequirePositions(List<RequirePositionDto> requirePositions) {
		this.requirePositions = requirePositions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, comment, createdDate, deleted, dueDate, id, requirePositions, status, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VacancyDto other = (VacancyDto) obj;
		return Objects.equals(code, other.code) && Objects.equals(comment, other.comment)
				&& Objects.equals(createdDate, other.createdDate) && deleted == other.deleted
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(id, other.id)
				&& Objects.equals(requirePositions, other.requirePositions) && status == other.status
				&& Objects.equals(team, other.team);
	}
	

}

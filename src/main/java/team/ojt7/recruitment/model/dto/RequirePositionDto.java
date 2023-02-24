package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import team.ojt7.recruitment.model.entity.RequirePosition;
import team.ojt7.recruitment.model.entity.Vacancy;

public class RequirePositionDto {

	private Long id;
	private boolean foc;

	@NotNull(message = "{notNull.requirePosition.count}")
	@Min(value = 1, message = "{min.requirePosition.count}")
	Integer count;

	@NotNull(message = "{notNull.requirePosition.team}")
	TeamDto team;

	@NotNull(message = "{notNull.requirePosition.position}")
	private PositionDto position;

	private VacancyDto vacancy;

	public static RequirePositionDto of(RequirePosition entity) {
		if (entity == null) {
			return null;
		}
		
		

		RequirePositionDto dto = new RequirePositionDto();
		dto.setId(entity.getId());
		dto.setCount(entity.getCount());
		dto.setFoc(entity.isFoc());
		dto.setPosition(PositionDto.of(entity.getPosition()));
		dto.setTeam(TeamDto.of(entity.getTeam()));
		
		if (entity.getVacancy() != null) {
			Vacancy vacancy = entity.getVacancy();
			VacancyDto vacancyDto = new VacancyDto();
			vacancyDto.setId(vacancy.getId());
			vacancyDto.setCode(vacancy.getCode());
			vacancyDto.setDueDate(vacancy.getDueDate());
			vacancyDto.setStatus(vacancy.getStatus());
			vacancyDto.setComment(vacancy.getComment());
			vacancyDto.setDeleted(vacancy.isDeleted());
			vacancyDto.setCreatedDate(vacancy.getCreatedDate());
			vacancyDto.setDepartment(DepartmentDto.of(vacancy.getDepartment()));
			vacancyDto.setCreatedUser(UserDto.of(vacancy.getCreatedUser()));
			dto.setVacancy(vacancyDto);
		}
		return dto;
	}

	public static List<RequirePositionDto> ofList(List<RequirePosition> entityList) {
		return entityList.stream().map(e -> of(e)).toList();
	}

	public static RequirePosition parse(RequirePositionDto dto) {
		if (dto == null) {
			return null;
		}

		RequirePosition entity = new RequirePosition();
		entity.setId(dto.getId());
		entity.setCount(dto.getCount());
		entity.setFoc(dto.isFoc());
		entity.setPosition(PositionDto.parse(dto.getPosition()));
		entity.setTeam(TeamDto.parse(dto.getTeam()));
		
		if (dto.getVacancy() != null) {
			VacancyDto vacancyDto = dto.getVacancy();
			Vacancy vacancy = new Vacancy();
			vacancy.setId(vacancyDto.getId());
			vacancy.setCode(vacancyDto.getCode());
			vacancy.setDueDate(vacancyDto.getDueDate());
			vacancy.setStatus(vacancyDto.getStatus());
			vacancy.setComment(vacancyDto.getComment());
			vacancy.setDeleted(vacancyDto.isDeleted());
			vacancy.setCreatedDate(vacancyDto.getCreatedDate());
			vacancy.setDepartment(DepartmentDto.parse(vacancyDto.getDepartment()));
			vacancy.setCreatedUser(UserDto.parse(vacancyDto.getCreatedUser()));
			entity.setVacancy(vacancy);
		}
		
		return entity;
	}

	public static List<RequirePosition> parseList(List<RequirePositionDto> dtoList) {
		return dtoList.stream().map(d -> parse(d)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFoc() {
		return foc;
	}

	public void setFoc(boolean foc) {
		this.foc = foc;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public TeamDto getTeam() {
		return team;
	}

	public void setTeam(TeamDto team) {
		this.team = team;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public VacancyDto getVacancy() {
		return vacancy;
	}

	public void setVacancy(VacancyDto vacancy) {
		this.vacancy = vacancy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, foc, id, position, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequirePositionDto other = (RequirePositionDto) obj;
		return count == other.count && foc == other.foc && Objects.equals(id, other.id)
				&& Objects.equals(position, other.position) && Objects.equals(team, other.team);
	}

}

package team.ojt7.recruitment.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

public class VacancyDto {

	private Long id;

	@NotBlank(message = "{notBlank.vacancy.code}")
	private String code;

	@NotNull(message = "{notNull.vacancy.department}")
	private DepartmentDto department;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdDate;

	@NotNull(message = "{notNull.vacancy.dueDate}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	@NotNull(message = "{notNull.vacancy.status}")
	private Status status;
	private String comment;
	private boolean deleted;

	private UserDto createdUser;

	@Valid
	@NotEmpty(message = "{notEmpty.vacancy.requirePositions}")
	private List<RequirePositionDto> requirePositions;

	public static VacancyDto of(Vacancy entity) {
		if (entity == null) {
			return null;
		}

		VacancyDto dto = new VacancyDto();
		dto.setId(entity.getId());
		dto.setCode(entity.getCode());
		dto.setDueDate(entity.getDueDate());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setDepartment(DepartmentDto.of(entity.getDepartment()));
		dto.setStatus(entity.getStatus());
		dto.setComment(entity.getComment());
		dto.setDeleted(entity.isDeleted());
		dto.setCreatedUser(UserDto.of(entity.getCreatedUser()));
		dto.setRequirePositions(RequirePositionDto.ofList(entity.getRequirePositions()));
		return dto;
	}

	public static List<VacancyDto> ofList(List<Vacancy> list) {
		return list.stream().map(d -> of(d)).toList();
	}

	public static Vacancy parse(VacancyDto dto) {
		if (dto == null) {
			return null;
		}

		Vacancy entity = new Vacancy();
		entity.setId(dto.getId());
		entity.setCode(dto.getCode());
		entity.setDueDate(dto.getDueDate());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setDepartment(DepartmentDto.parse(dto.getDepartment()));
		entity.setStatus(dto.getStatus());
		entity.setComment(dto.getComment());
		entity.setDeleted(dto.isDeleted());
		entity.setCreatedUser(UserDto.parse(dto.getCreatedUser()));
		entity.setRequirePositions(RequirePositionDto.parseList(dto.getRequirePositions()));
		return entity;
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

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
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

	public UserDto getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(UserDto createdUser) {
		this.createdUser = createdUser;
	}
	
	public boolean isExpired() {
		return status == Status.OPENING && dueDate.isBefore(LocalDate.now());
	}

	public List<RequirePositionDto> getRequirePositions() {
		return requirePositions;
	}

	public void setRequirePositions(List<RequirePositionDto> requirePositions) {
		this.requirePositions = requirePositions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, comment, createdDate, createdUser, deleted, department, dueDate, id, requirePositions,
				status);
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
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(createdUser, other.createdUser)
				&& deleted == other.deleted && Objects.equals(department, other.department)
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(id, other.id)
				&& Objects.equals(requirePositions, other.requirePositions) && status == other.status;
	}

}

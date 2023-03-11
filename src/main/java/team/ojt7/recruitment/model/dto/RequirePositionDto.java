package team.ojt7.recruitment.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.entity.Interview;
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

	private List<ApplicantDto> applicants = new ArrayList<>();

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
			vacancyDto.setCreatedDateTime(vacancy.getCreatedDateTime());
			vacancyDto.setDepartment(DepartmentDto.of(vacancy.getDepartment()));
			vacancyDto.setCreatedUser(UserDto.of(vacancy.getCreatedUser()));
			dto.setVacancy(vacancyDto);
		}

		if (entity.getApplicants() != null && !entity.getApplicants().isEmpty()) {
			List<ApplicantDto> applicants = new ArrayList<>();
			for (Applicant applicant : entity.getApplicants()) {
				ApplicantDto applicantDto = new ApplicantDto();
				applicantDto.setId(applicant.getId());
				applicantDto.setCode(applicant.getCode());
				applicantDto.setName(applicant.getName());
				applicantDto.setEmail(applicant.getEmail());
				applicantDto.setPhone(applicant.getPhone());
				applicantDto.setAddress(applicant.getAddress());
				applicantDto.setStatus(applicant.getStatus());
				applicantDto.setExperience(applicant.getExperience());
				applicantDto.setEducation(applicant.getEducation());
				applicantDto.setSkill(applicant.getSkill());
				applicantDto.setAttachedUri(applicant.getAttachedUri());
				applicantDto.setRecruitmentResource(RecruitmentResourceDto.of(applicant.getRecruitmentResource()));
				applicantDto.setCreatedDate(applicant.getCreatedDate());
				applicantDto.setCreatedUser(UserDto.of(applicant.getCreatedUser()));
				applicantDto.setDeleted(applicant.isDeleted());
				
				for (Interview interview : applicant.getInterviews()) {
					InterviewDto interviewDto = new InterviewDto();
					interviewDto.setId(interview.getId());
					interviewDto.setCode(interview.getCode());
					interviewDto.setInterviewName(InterviewNameDto.of(interview.getInterviewName()));
					interviewDto.setComment(interview.getComment());
					interviewDto.setLocalDate(interview.getDateTime().toLocalDate());
					interviewDto.setLocalTime(interview.getDateTime().toLocalTime());
					interviewDto.setApplicant(applicantDto);
					interviewDto.setStatus(interview.getStatus());
					interviewDto.setCreatedDateTime(interview.getCreatedDateTime());
					applicantDto.getInterviews().add(interviewDto);
				}
				applicantDto.getInterviews().sort((i, j) -> {
					if (i.getCreatedDateTime().isBefore(j.getCreatedDateTime())) {
						return 1;
					} else if (i.getCreatedDateTime().isAfter(j.getCreatedDateTime())) {
						return -1;
					}
					return 0;
				});
				
				if (applicant.getVacancy() != null) {
					Vacancy vacancy = applicant.getVacancy();
					VacancyDto vacancyDto = new VacancyDto();
					vacancyDto.setId(vacancy.getId());
					vacancyDto.setCode(vacancy.getCode());
					vacancyDto.setDueDate(vacancy.getDueDate());
					vacancyDto.setStatus(vacancy.getStatus());
					vacancyDto.setCreatedDateTime(vacancy.getCreatedDateTime());
					vacancyDto.setCreatedUser(UserDto.of(vacancy.getCreatedUser()));
					vacancyDto.setDeleted(vacancy.isDeleted());
					vacancyDto.setComment(vacancy.getComment());
					vacancyDto.setDepartment(DepartmentDto.of(vacancy.getDepartment()));
					applicantDto.setVacancy(vacancyDto);
				}
				
				applicants.add(applicantDto);
			}
			
			
			dto.setApplicants(applicants);
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
			vacancy.setCreatedDateTime(vacancyDto.getCreatedDateTime());
			vacancy.setDepartment(DepartmentDto.parse(vacancyDto.getDepartment()));
			vacancy.setCreatedUser(UserDto.parse(vacancyDto.getCreatedUser()));
			entity.setVacancy(vacancy);
		}
		
		if (dto.getApplicants() != null && !dto.getApplicants().isEmpty()) {
			List<Applicant> applicants = new ArrayList<>();
			for (ApplicantDto appDto : dto.getApplicants()) {
				Applicant applicant = new Applicant();
				applicant.setId(appDto.getId());
				applicant.setCode(appDto.getCode());
				applicant.setName(appDto.getName());
				applicant.setEmail(appDto.getEmail());
				applicant.setPhone(appDto.getPhone());
				applicant.setAddress(appDto.getAddress());
				applicant.setStatus(appDto.getStatus());
				applicant.setExperience(appDto.getExperience());
				applicant.setEducation(appDto.getEducation());
				applicant.setSkill(appDto.getSkill());
				applicant.setAttachedUri(appDto.getAttachedUri());
				applicant.setRecruitmentResource(RecruitmentResourceDto.parse(appDto.getRecruitmentResource()));
				applicant.setCreatedDate(appDto.getCreatedDate());
				applicant.setCreatedUser(UserDto.parse(appDto.getCreatedUser()));
				applicant.setVacancy(VacancyDto.parse(appDto.getVacancy()));
				applicant.setDeleted(appDto.isDeleted());
				
				if (appDto.getVacancy() != null) {
					VacancyDto vacancyDto = appDto.getVacancy();
					Vacancy vacancy = new Vacancy();
					vacancy.setId(vacancyDto.getId());
					vacancy.setCode(vacancyDto.getCode());
					vacancy.setDueDate(vacancyDto.getDueDate());
					vacancy.setStatus(vacancyDto.getStatus());
					vacancy.setCreatedDateTime(vacancyDto.getCreatedDateTime());
					vacancy.setCreatedUser(UserDto.parse(vacancyDto.getCreatedUser()));
					vacancy.setDeleted(vacancyDto.isDeleted());
					vacancy.setComment(vacancyDto.getComment());
					vacancy.setDepartment(DepartmentDto.parse(vacancyDto.getDepartment()));
					applicant.setVacancy(vacancy);
				}
				
				applicants.add(applicant);
			}
			entity.setApplicants(applicants);
		}

		return entity;
	}

	public static List<RequirePosition> parseList(List<RequirePositionDto> dtoList) {
		if (dtoList == null) {
			return Collections.emptyList();
		}
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

	public List<ApplicantDto> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<ApplicantDto> applicants) {
		this.applicants = applicants;
	}
	
	public List<ApplicantDto> getActiveApplicants() {
		return applicants.stream().filter(a -> !a.isDeleted()).toList();
	}
	
	public Long getHiredCount() {
		return getActiveApplicants().stream().filter(a -> a.getStatus() == Status.HIRED).count();
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

package team.ojt7.recruitment.model.dto;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.entity.ApplicantStatusChangeHistory;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.RequirePosition;

public class ApplicantDto {
	private Long id;
	@NotBlank(message = "NotBlank.applicant.code")
	private String code;
	@NotBlank(message = "NotBlank.applicant.name")
	private String name;
	@NotBlank(message = "NotBlank.applicant.phone")
	@Size(min = 6, max = 16, message = "{invalidSize.applicant.phone}")
	@Pattern(regexp = "\\d+", message = "{invalid.phone}")
	private String phone;
	@NotBlank(message = "NotBlank.applicant.email")
	@Pattern(regexp = "^(.+)@(.+)$", message = "{invalid.email}")
	private String email;

	private Status status = Status.NEW;

	private String address;
	private String experience;
	private String education;
	private String skill;
//	@NotBlank(message = "NotBlank.applicant.attachedUri")
	private String attachedUri;

	private LocalDate joinDate;

	@NotNull(message = "Select a recruitment resource")
	private RecruitmentResourceDto recruitmentResource;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdDate;

	private LocalDateTime hiredDateTime;

	private UserDto createdUser;

	@NotNull(message = "Select a vacancy")
	private VacancyDto vacancy;

	@NotNull(message = "Select a position")
	private RequirePositionDto requirePosition;

	private boolean isDeleted;

	private boolean updatableStatus;

	private List<ApplicantStatusChangeHistoryDto> statusChangeHistories = new ArrayList<>();

	private List<InterviewDto> interviews = new ArrayList<>();

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getHiredDateTime() {
		return hiredDateTime;
	}

	public void setHiredDateTime(LocalDateTime hiredDateTime) {
		this.hiredDateTime = hiredDateTime;
	}

	public String getStatusColorClass() {
		return switch (status) {
		case NEW -> "bg-primary";
		case UNQULIFIED -> "bg-secondary";
		case QULIFIED -> "bg-info text-dark";
		case SELECTED -> "bg-orangered";
		case REJECTED -> "bg-danger";
		case JOB_OFFERED -> "bg-warning text-dark";
		case DID_NOT_JOIN -> "bg-dark";
		case HIRED -> "bg-success";
		default -> null;
		};
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public List<InterviewDto> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<InterviewDto> interviews) {
		interviews.sort((i, j) -> {
			LocalDateTime iTime = LocalDateTime.of(i.getLocalDate(), i.getLocalTime());
			LocalDateTime jTime = LocalDateTime.of(j.getLocalDate(), j.getLocalTime());
			if (iTime.isBefore(jTime)) {
				return 1;
			} else if (iTime.isAfter(jTime)) {
				return -1;
			}
			return 0;
		});
		this.interviews = interviews;
	}

	public InterviewDto getLastestInterview() {
		try {
			return interviews.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public InterviewDto getActiveInterview() {
		InterviewDto lastesetInterview = getLastestInterview();
		if (lastesetInterview == null) {
			return null;
		}
		return lastesetInterview.getStatus() == team.ojt7.recruitment.model.entity.Interview.Status.NOT_START_YET
				? lastesetInterview
				: null;
	}

	public boolean isAvailableForNewInterview() {
		return getStatus().getStep() > 2
				&& (getLastestInterview() == null || getLastestInterview().getStatus().getStep() > 2)
				&& getStatus() != Status.HIRED;
	}

	public boolean isUpdatableStatus() {
		return updatableStatus;
	}

	public void setUpdatableStatus(boolean updatableStatus) {
		this.updatableStatus = updatableStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getAttachedUri() {
		return attachedUri;
	}

	public void setAttachedUri(String attachedUri) {
		this.attachedUri = attachedUri;
	}

	public String getAttachedFileName() {
		if (attachedUri == null || attachedUri.isBlank()) {
			return null;
		}
		int separatorIndex = attachedUri.lastIndexOf(File.separator);
		return separatorIndex == -1 ? attachedUri : attachedUri.substring(separatorIndex + 1);
	}

	public RecruitmentResourceDto getRecruitmentResource() {
		return recruitmentResource;
	}

	public void setRecruitmentResource(RecruitmentResourceDto recruitmentResource) {
		this.recruitmentResource = recruitmentResource;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public UserDto getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(UserDto createdUser) {
		this.createdUser = createdUser;
	}

	public VacancyDto getVacancy() {
		return vacancy;
	}

	public void setVacancy(VacancyDto vacancy) {
		this.vacancy = vacancy;
	}

	public RequirePositionDto getRequirePosition() {
		return requirePosition;
	}

	public void setRequirePosition(RequirePositionDto requirePosition) {
		this.requirePosition = requirePosition;
	}

	public List<ApplicantStatusChangeHistoryDto> getStatusChangeHistories() {
		return statusChangeHistories;
	}

	public void setStatusChangeHistories(List<ApplicantStatusChangeHistoryDto> statusChangeHistories) {
		this.statusChangeHistories = statusChangeHistories;
	}

	public static ApplicantDto of(Applicant applicant) {
		if (applicant == null) {
			return null;
		}
		ApplicantDto dto = new ApplicantDto();
		dto.setId(applicant.getId());
		dto.setCode(applicant.getCode());
		dto.setName(applicant.getName());
		dto.setEmail(applicant.getEmail());
		dto.setPhone(applicant.getPhone());
		dto.setAddress(applicant.getAddress());
		dto.setExperience(applicant.getExperience());
		dto.setEducation(applicant.getEducation());
		dto.setSkill(applicant.getSkill());
		dto.setStatus(applicant.getStatus());
		dto.setAttachedUri(applicant.getAttachedUri());
		dto.setRecruitmentResource(RecruitmentResourceDto.of(applicant.getRecruitmentResource()));
		dto.setCreatedDate(applicant.getCreatedDate());
		dto.setCreatedUser(UserDto.of(applicant.getCreatedUser()));
		dto.setVacancy(VacancyDto.of(applicant.getVacancy()));
		dto.setJoinDate(applicant.getJoinDate());
		dto.setHiredDateTime(applicant.getHiredDateTime());

		if (applicant.getRequirePosition() != null) {
			RequirePosition requirePosition = applicant.getRequirePosition();
			RequirePositionDto requirePositionDto = new RequirePositionDto();
			requirePositionDto.setId(requirePosition.getId());
			requirePositionDto.setPosition(PositionDto.of(requirePosition.getPosition()));
			requirePositionDto.setFoc(requirePosition.isFoc());
			requirePositionDto.setCount(requirePosition.getCount());
			requirePositionDto.setTeam(TeamDto.of(requirePosition.getTeam()));
			requirePositionDto.setVacancy(VacancyDto.of(requirePosition.getVacancy()));
			dto.setRequirePosition(requirePositionDto);
		}

		if (applicant.getStatusChangeHistories() != null) {
			for (ApplicantStatusChangeHistory asch : applicant.getStatusChangeHistories()) {
				ApplicantStatusChangeHistoryDto aschDto = new ApplicantStatusChangeHistoryDto();
				aschDto.setId(asch.getId());
				aschDto.setStatus(asch.getStatus());
				aschDto.setApplicant(dto);
				aschDto.setRemark(asch.getRemark());
				aschDto.setUpdatedOn(asch.getUpdatedOn());
				aschDto.setUpdatedBy(UserDto.of(asch.getUpdatedBy()));
				dto.statusChangeHistories.add(aschDto);
			}
		}

		if (applicant.getInterviews() != null) {
			for (Interview interview : applicant.getInterviews()) {
				InterviewDto interviewDto = new InterviewDto();
				interviewDto.setId(interview.getId());
				interviewDto.setCode(interview.getCode());
				interviewDto.setApplicant(dto);
				interviewDto.setInterviewName(InterviewNameDto.of(interview.getInterviewName()));
				interviewDto.setLocalDate(interview.getDateTime().toLocalDate());
				interviewDto.setLocalTime(interview.getDateTime().toLocalTime());
				interviewDto.setComment(interview.getComment());
				interviewDto.setStatus(interview.getStatus());
				interviewDto.setOwner(UserDto.of(interview.getOwner()));
				interviewDto.setUpdatedOn(interview.getUpdatedOn());
				interviewDto.setCreatedDateTime(interview.getCreatedDateTime());
				dto.interviews.add(interviewDto);
			}
			dto.interviews.sort((i, j) -> {
				if (i.getCreatedDateTime().isBefore(j.getCreatedDateTime())) {
					return 1;
				} else if (i.getCreatedDateTime().isAfter(j.getCreatedDateTime())) {
					return -1;
				}
				return 0;
			});
		}

		return dto;

	}

	public static Applicant parse(ApplicantDto dto) {
		if (dto == null) {
			return null;
		}
		Applicant applicant = new Applicant();
		applicant.setId(dto.getId());
		applicant.setCode(dto.getCode());
		applicant.setName(dto.getName());
		applicant.setEmail(dto.getEmail());
		applicant.setPhone(dto.getPhone());
		applicant.setAddress(dto.getAddress());
		applicant.setExperience(dto.getExperience());
		applicant.setEducation(dto.getEducation());
		applicant.setSkill(dto.getSkill());
		applicant.setStatus(dto.getStatus());
		applicant.setAttachedUri(dto.getAttachedUri());
		applicant.setRecruitmentResource(RecruitmentResourceDto.parse(dto.getRecruitmentResource()));
		applicant.setCreatedDate(dto.getCreatedDate());
		applicant.setCreatedUser(UserDto.parse(dto.getCreatedUser()));
		applicant.setVacancy(VacancyDto.parse(dto.getVacancy()));
		applicant.setJoinDate(dto.getJoinDate());
		applicant.setHiredDateTime(dto.getHiredDateTime());

		if (dto.getRequirePosition() != null) {
			RequirePositionDto requirePositionDto = dto.getRequirePosition();
			RequirePosition requirePosition = new RequirePosition();
			requirePosition.setId(requirePositionDto.getId());
			requirePosition.setPosition(PositionDto.parse(requirePositionDto.getPosition()));
			requirePosition.setFoc(requirePositionDto.isFoc());
			requirePosition.setCount(requirePositionDto.getCount());
			requirePosition.setTeam(TeamDto.parse(requirePositionDto.getTeam()));
			requirePosition.setVacancy(VacancyDto.parse(requirePositionDto.getVacancy()));
			applicant.setRequirePosition(requirePosition);
		}

		if (dto.getStatusChangeHistories() != null) {
			for (ApplicantStatusChangeHistoryDto aschDto : dto.getStatusChangeHistories()) {
				ApplicantStatusChangeHistory asch = new ApplicantStatusChangeHistory();
				asch.setId(aschDto.getId());
				asch.setStatus(aschDto.getStatus());
				asch.setApplicant(applicant);
				asch.setRemark(aschDto.getRemark());
				asch.setUpdatedOn(aschDto.getUpdatedOn());
				asch.setUpdatedBy(UserDto.parse(aschDto.getUpdatedBy()));
				applicant.getStatusChangeHistories().add(asch);
			}
		}

		if (dto.getInterviews() != null) {
			for (InterviewDto interviewDto : dto.getInterviews()) {
				Interview interview = new Interview();
				interview.setId(interviewDto.getId());
				interview.setCode(interviewDto.getCode());
				interview.setInterviewName(InterviewNameDto.parse(interviewDto.getInterviewName()));
				interview.setStatus(interviewDto.getStatus());
				interview.setApplicant(applicant);
				interview.setDateTime(LocalDateTime.of(interviewDto.getLocalDate(), interviewDto.getLocalTime()));
				interview.setComment(interviewDto.getComment());
				interview.setOwner(UserDto.parse(interviewDto.getOwner()));
				interview.setUpdatedOn(interviewDto.getUpdatedOn());
				interview.setCreatedDateTime(interviewDto.getCreatedDateTime());
				applicant.getInterviews().add(interview);
			}
		}

		return applicant;

	}

	public static List<ApplicantDto> ofList(List<Applicant> list) {
		return list.stream().map(d -> of(d)).toList();
	}

	public static List<Applicant> parseList(List<ApplicantDto> dtoList) {
		return dtoList.stream().map(d -> parse(d)).toList();
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, attachedUri, code, createdDate, createdUser, education, email, experience, id,
				isDeleted, name, phone, recruitmentResource, requirePosition, skill, status, vacancy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicantDto other = (ApplicantDto) obj;
		return Objects.equals(address, other.address) && Objects.equals(attachedUri, other.attachedUri)
				&& Objects.equals(code, other.code) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(createdUser, other.createdUser) && Objects.equals(education, other.education)
				&& Objects.equals(email, other.email) && Objects.equals(experience, other.experience)
				&& Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone) && Objects.equals(recruitmentResource, other.recruitmentResource)
				&& Objects.equals(requirePosition, other.requirePosition) && Objects.equals(skill, other.skill)
				&& status == other.status && Objects.equals(vacancy, other.vacancy);
	}

}

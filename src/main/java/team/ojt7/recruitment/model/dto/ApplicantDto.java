package team.ojt7.recruitment.model.dto;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Applicant.Status;
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

	private Status status;

	private String address;
	@NotBlank(message = "NotBlank.applicant.experience")
	private String experience;
	@NotBlank(message = "NotBlank.applicant.education")
	private String education;
	@NotBlank(message = "NotBlank.applicant.skill")
	private String skill;
//	@NotBlank(message = "NotBlank.applicant.attachedUri")
	private String attachedUri;

	@NotNull(message = "Select a recruitment resource")
	private RecruitmentResourceDto recruitmentResource;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdDate;

	private UserDto createdUser;

	@NotNull(message = "Select a vacancy")
	private VacancyDto vacancy;

	@NotNull(message = "Select a position")
	private RequirePositionDto requirePosition;

	private boolean isDeleted;

	private List<InterviewDto> interviews;

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
		this.interviews = interviews;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
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

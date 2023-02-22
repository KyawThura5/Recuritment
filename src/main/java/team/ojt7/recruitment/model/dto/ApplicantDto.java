package team.ojt7.recruitment.model.dto;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import team.ojt7.recruitment.model.entity.Applicant;

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

	private String address;
	@NotBlank(message = "NotBlank.applicant.experience")
	private String experience;
	@NotBlank(message = "NotBlank.applicant.education")
	private String education;
	@NotBlank(message = "NotBlank.applicant.skill")
	private String skill;
//	@NotBlank(message = "NotBlank.applicant.attachedUri")
	private String attachedUri;

	private RecruitmentResourceDto recruitmentResource;

	private LocalDateTime createdDate;

	private UserDto createdUser;

	private RequirePositionDto requirePosition;

	private boolean isDeleted;

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
		dto.setAttachedUri(applicant.getAttachedUri());
		dto.setRecruitmentResource(RecruitmentResourceDto.of(applicant.getRecruitmentResource()));
		dto.setCreatedDate(applicant.getCreatedDate());
		dto.setCreatedUser(UserDto.of(applicant.getCreatedUser()));
		dto.setRequirePosition(RequirePositionDto.of(applicant.getRequirePosition()));
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
		applicant.setAttachedUri(dto.getAttachedUri());
		applicant.setRecruitmentResource(RecruitmentResourceDto.parse(dto.getRecruitmentResource()));
		applicant.setCreatedDate(dto.getCreatedDate());
		applicant.setCreatedUser(UserDto.parse(dto.getCreatedUser()));
		applicant.setRequirePosition(RequirePositionDto.parse(dto.getRequirePosition()));
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
				name, phone, recruitmentResource, requirePosition, skill);
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
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone) && Objects.equals(recruitmentResource, other.recruitmentResource)
				&& Objects.equals(requirePosition, other.requirePosition) && Objects.equals(skill, other.skill);
	}

}

package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="applicant")
public class Applicant implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String code;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String phone;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String address;
	
	private String experience;
	
//	@Column(nullable=false)
	private String education;
	
	@Column(nullable=false)
	private String skill;
	
	private String attachedUri;
	
	@ManyToOne()
	@JoinColumn(name="recruitment_resource_id")
	private RecruitmentResource recruitmentResource;
	
	@Column(name = "created_date", nullable = false, updatable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDate;
	
	@ManyToOne()
	@JoinColumn(name="created_user_id")
	private User createdUser;
	
	@ManyToOne()
	@JoinColumn(name="require_position_id")
	private RequirePosition requirePosition;
	
	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
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

	public RecruitmentResource getRecruitmentResource() {
		return recruitmentResource;
	}

	public void setRecruitmentResource(RecruitmentResource recruitmentResource) {
		this.recruitmentResource = recruitmentResource;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public RequirePosition getRequirePosition() {
		return requirePosition;
	}

	public void setRequirePosition(RequirePosition requirePosition) {
		this.requirePosition = requirePosition;
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
		Applicant other = (Applicant) obj;
		return Objects.equals(address, other.address) && Objects.equals(attachedUri, other.attachedUri)
				&& Objects.equals(code, other.code) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(createdUser, other.createdUser) && Objects.equals(education, other.education)
				&& Objects.equals(email, other.email) && Objects.equals(experience, other.experience)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone) && Objects.equals(recruitmentResource, other.recruitmentResource)
				&& Objects.equals(requirePosition, other.requirePosition) && Objects.equals(skill, other.skill);
	}

	
	
}

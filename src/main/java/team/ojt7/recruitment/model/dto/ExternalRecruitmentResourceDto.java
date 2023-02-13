package team.ojt7.recruitment.model.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import team.ojt7.recruitment.model.entity.ExternalRecruitmentResource.Type;

public class ExternalRecruitmentResourceDto extends RecruitmentResourceDto {

	@NotBlank(message = "{notBlank.externalRecruitmentResource.pic}")
	private String pic;
	
	@NotBlank(message = "{notBlank.externalRecruitmentResource.contactPerson}")
	private String contactPerson;
	
	@Pattern(regexp = "\\d+", message = "{invalid.phone}")
	@Size(min = 6, max = 16, message = "{invalidSize.externalRecruitmentResource.phone}")
	@NotBlank(message = "{notBlank.externalRecruitmentResource.phone}")
	private String phone;
	
	@Pattern(regexp = "^(.+)@(.+)$", message = "{invalid.email}")
	@NotBlank(message = "{notBlank.externalRecruitmentResource.email}")
	private String email;
	
	private String address;
	
	@NotNull(message = "{notNull.externalRecruitmentResource.type}")
	private Type type;
	
	private String websiteLink;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, contactPerson, email, phone, pic, type, websiteLink);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalRecruitmentResourceDto other = (ExternalRecruitmentResourceDto) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactPerson, other.contactPerson)
				&& Objects.equals(email, other.email) && Objects.equals(phone, other.phone)
				&& Objects.equals(pic, other.pic) && type == other.type
				&& Objects.equals(websiteLink, other.websiteLink);
	}

}

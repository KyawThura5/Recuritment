package team.ojt7.recruitment.model.entity;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "external_recruitment_resource")
public class ExternalRecruitmentResource extends RecruitmentResource {

	private static final long serialVersionUID = 1L;

	private String pic;
	
	@Column(name = "contact_person")
	private String contactPerson;
	private String phone;
	private String email;
	private String address;

	@Enumerated(EnumType.STRING)
	private Type type;

	private String websiteLink;

	public enum Type {
		
		AGENCY("AGENCY"),
		UNIVERSITY("UNIVERSITY"), 
		TRAINING_CENTER("TRAINING CENTER");

		private String displayName;

		Type(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
		}
	}
	

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
		ExternalRecruitmentResource other = (ExternalRecruitmentResource) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactPerson, other.contactPerson)
				&& Objects.equals(email, other.email) && Objects.equals(phone, other.phone)
				&& Objects.equals(pic, other.pic) && type == other.type
				&& Objects.equals(websiteLink, other.websiteLink);
	}

	
}
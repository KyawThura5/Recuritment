package team.ojt7.recruitment.model.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "external_recruitment_resource")
public class External_Recruitment_Resource extends Recruitment_Resource{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private String pic;
	private String contact_person;
	private String phone;
	private String email;
	private String address;

	@Enumerated(EnumType.STRING)
	private Type type;

	private String websitelink;

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

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
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

	public String getWebsitelink() {
		return websitelink;
	}

	public void setWebsitelink(String websitelink) {
		this.websitelink = websitelink;
	}


}

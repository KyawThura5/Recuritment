package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "user",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"code", "is_deleted"}),
		@UniqueConstraint(columnNames = {"email", "is_deleted"}),
		@UniqueConstraint(columnNames = {"phone", "is_deleted"}),
	}
)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	private String code;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String email;
	
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String password;
	
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private boolean isDeleted;

	public enum Role {
		ADMIN("Admin"),
		GENERAL_MANAGER("General Manager"),
		DEPARTMENT_HEAD("Department Head"),
		PROJECT_MANAGER("Project Manager"),
		HIRING_MANAGER("Hiring Manager");
		
		private String displayName;
		
		Role(String displayName) {
			this.displayName = displayName;
		}
		
		public String getDisplayName() {
			return displayName;
		}
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, email, gender, id, isDeleted, name, password, phone, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(code, other.code) && Objects.equals(email, other.email) && gender == other.gender
				&& Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone) && role == other.role;
	}

}
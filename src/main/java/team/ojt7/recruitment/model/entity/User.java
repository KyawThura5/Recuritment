package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
	private Long id;
	private String code;
	private String name;
	private Role role;
	private String email;
	private String phone;
	private String password;
	private boolean isDeleted;
	
	 @OneToOne(cascade=CascadeType.PERSIST)
	 @JoinColumn(name="address_id")
	 private Address address; 

	public enum Role {
		ADMIN, GENERAL_MANAGER, DEPARTMENT_HEAD, PROJECT_MANAGER, HIRING_MANAGER
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, code, email, id, isDeleted, name, password, phone, role);
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
		return Objects.equals(address, other.address) && Objects.equals(code, other.code)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id) && isDeleted == other.isDeleted
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && role == other.role;
	}

}
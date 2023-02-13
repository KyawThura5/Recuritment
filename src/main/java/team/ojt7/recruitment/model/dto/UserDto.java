package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Range;

import org.hibernate.validator.constraints.Range;

import team.ojt7.recruitment.model.entity.Gender;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;

public class UserDto {
	
	private Long id;
	@NotEmpty
	private String code;
	@NotEmpty
	private String name;
	@Email
	@NotBlank(message = "Email is required!")
	private String email;
	@NotNull
	private Role role;
	@NotNull
	private Gender gender;
	@NotEmpty
	@Range(min=6,max=16,message = "Invalid Phone Number")
	private String phone;
	@NotEmpty
	private String password;
	@NotEmpty
	private String confirmPassword;
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	private boolean isDeleted;
	
	public static UserDto of(User user) {
		if (user == null) {
			return null;
		}
		
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setCode(user.getCode());
		userDto.setName(user.getName());
		userDto.setRole(user.getRole());
		userDto.setGender(user.getGender());
		userDto.setEmail(user.getEmail());
		userDto.setPhone(user.getPhone());
		userDto.setDeleted(user.isDeleted());
		userDto.setPassword(user.getPassword());
		return userDto;
	}
	
	public static List<UserDto> ofList(List<User> users) {
		return users.stream().map(u -> UserDto.of(u)).toList();
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
		UserDto other = (UserDto) obj;
		return Objects.equals(code, other.code) && Objects.equals(email, other.email) && gender == other.gender
				&& Objects.equals(id, other.id) && isDeleted == other.isDeleted && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone) && role == other.role;
	}

	

}
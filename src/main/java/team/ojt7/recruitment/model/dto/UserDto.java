package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import team.ojt7.recruitment.model.entity.Gender;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;

public class UserDto {
	
	private Long id;
	
	@NotBlank(message="NotBlank.user.code")
	private String code;
	
	@NotBlank(message="NotBlank.user.name")
	private String name;
	
	@NotBlank(message="NotBlank.user.email")
	@Pattern(regexp = "^(.+)@(.+)$", message = "{invalid.email}")
	private String email;
	
	@NotNull(message="NotNull.user.role")
	private Role role;
	
	@NotNull(message="NotNull.user.gender")
	private Gender gender;
	
	@NotBlank(message = "{notBlank.user.phone}")
	@Size(min = 6, max = 16, message = "{invalidSize.user.phone}")
	@Pattern(regexp = "\\d+", message = "{invalid.phone}")
	private String phone;
	
	@NotBlank(message="NotBlank.user.password")
	@Size(min = 8, max = 30, message = "{invalidSize.user.password}")
	@Pattern(regexp="\\S+",message="{invalid.password}")
	private String password;
	
	@NotBlank(message="NotBlank.user.confirmPassword")
	@Size(min = 8, max = 30, message  = "{invalidSize.user.confirmPassword}")
	@Pattern(regexp="\\S+",message="{invalid.confirmPassword}")
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
		userDto.setConfirmPassword(user.getPassword());
		return userDto;
	}
	
	public static List<UserDto> ofList(List<User> users) {
		return users.stream().map(u -> UserDto.of(u)).toList();
	}
	
	public static User parse(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setCode(dto.getCode());
		user.setName(dto.getName());
		user.setRole(dto.getRole());
		user.setGender(dto.getGender());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setDeleted(dto.isDeleted());
		user.setPassword(dto.getPassword());
		return user;
	}
	
	public static List<User> parseList(List<UserDto> dtos) {
		return dtos.stream().map(u -> parse(u)).toList();
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
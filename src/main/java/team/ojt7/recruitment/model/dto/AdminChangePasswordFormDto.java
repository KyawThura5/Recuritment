package team.ojt7.recruitment.model.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import team.ojt7.recruitment.model.validator.NotBlankGroup;
import team.ojt7.recruitment.model.validator.NotNullGroup;
import team.ojt7.recruitment.model.validator.PatternGroup;
import team.ojt7.recruitment.model.validator.SizeGroup;

public class AdminChangePasswordFormDto {

	@NotNull(groups = NotNullGroup.class)
	private Long userId;
	
	@NotBlank(message="{NotBlank.user.password}", groups = NotBlankGroup.class)
	@Pattern(regexp="\\S+",message="{invalid.password}", groups = PatternGroup.class)
	@Size(min = 8, max = 30, message = "{invalidSize.user.password}", groups = SizeGroup.class)
	private String password;
	
	@NotBlank(message="{NotBlank.user.confirmPassword}", groups = NotBlankGroup.class)
	private String confirmPassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(confirmPassword, password, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminChangePasswordFormDto other = (AdminChangePasswordFormDto) obj;
		return Objects.equals(confirmPassword, other.confirmPassword) && Objects.equals(password, other.password)
				&& Objects.equals(userId, other.userId);
	}

}

package team.ojt7.recruitment.model.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserChangePasswordFormDto {

	@NotNull
	private Long userId;
	
	@NotBlank(message = "{notBlank.user.oldPassword}")
	private String oldPassword;
	
	@NotBlank(message="{notBlank.user.newPassword}")
	@Size(min = 8, max = 30, message = "{invalidSize.user.password}")
	@Pattern(regexp="\\S+",message="{invalid.password}")
	private String newPassword;
	
	@NotBlank(message = "{NotBlank.user.confirmPassword}")
	private String confirmPassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(confirmPassword, newPassword, oldPassword, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserChangePasswordFormDto other = (UserChangePasswordFormDto) obj;
		return Objects.equals(confirmPassword, other.confirmPassword) && Objects.equals(newPassword, other.newPassword)
				&& Objects.equals(oldPassword, other.oldPassword) && Objects.equals(userId, other.userId);
	}

}

package team.ojt7.recruitment.model.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import team.ojt7.recruitment.model.dto.UserChangePasswordFormDto;

@Component
public class UserChangePasswordFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UserChangePasswordFormDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof UserChangePasswordFormDto form) {
			if (Objects.equals(form.getOldPassword(), form.getNewPassword())) {
				errors.rejectValue("newPassword", "same", "New password cannot be same with old password");
			} else {
				if (!Objects.equals(form.getNewPassword(), form.getConfirmPassword())) {
					errors.rejectValue("confirmPassword", "notSame", "Passwords are not the same");
				}
			}
		}
	}
}

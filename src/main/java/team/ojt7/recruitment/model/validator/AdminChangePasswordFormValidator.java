package team.ojt7.recruitment.model.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import team.ojt7.recruitment.model.dto.AdminChangePasswordFormDto;

@Component
public class AdminChangePasswordFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AdminChangePasswordFormDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof AdminChangePasswordFormDto form) {
			if (!Objects.equals(form.getPassword(), form.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "notSame", "Passwords are not the same");
			}
		}
	}

}

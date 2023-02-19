package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.service.UserService;

@Component
public class IdToUserFormatter implements Formatter<UserDto> {

	@Autowired
	private UserService userService;

	@Override
	public String print(UserDto object, Locale locale) {
		if (object != null) {
			return object.getId() == null ? null : object.getId().toString();
		}
		return null;
	}

	@Override
	public UserDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return userService.findById(id).orElse(null);
		}
		return null;
	}

}

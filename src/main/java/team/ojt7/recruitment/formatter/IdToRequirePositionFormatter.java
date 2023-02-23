package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.service.RequiredPositionService;

@Component
public class IdToRequirePositionFormatter implements Formatter<RequirePositionDto> {

	@Autowired
	private RequiredPositionService requiredPositionService;
	
	@Override
	public String print(RequirePositionDto object, Locale locale) {
		if (object != null) {
			return object.toString();
		}
		return null;
	}

	@Override
	public RequirePositionDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return requiredPositionService.findById(id).orElse(null);
		}
		return null;
	}

	

}

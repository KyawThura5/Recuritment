package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.service.PositionService;

@Component
public class IdToPositionFormatter implements Formatter<PositionDto> {

	@Autowired
	private PositionService positionService;
	
	@Override
	public String print(PositionDto object, Locale locale) {
		if (object != null) {
			return object.getName();
		}
		return null;
	}

	@Override
	public PositionDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return positionService.findById(id).orElse(null);
		}
		return null;
	}

}

package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.service.VacancyService;

@Component
public class IdToVacancyFormatter implements Formatter<VacancyDto> {
	
	@Autowired
	private VacancyService vacancyService;

	@Override
	public String print(VacancyDto object, Locale locale) {
		if (object != null) {
			return object.getCode();
		}
		return null;
	}

	@Override
	public VacancyDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return vacancyService.findById(id).orElse(null);
		}
		return null;
	}

}

package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.service.InterviewNameService;

@Component
public class IdToInterviewNameFormatter implements Formatter<InterviewNameDto>{
@Autowired
InterviewNameService interviewNameService;

@Override
public String print(InterviewNameDto object, Locale locale) {
	if (object != null) {
		return object.getName();
	}
	return null;
}

	@Override
	public InterviewNameDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return interviewNameService.findById(id).orElse(null);
		}
		return null;
	}

}

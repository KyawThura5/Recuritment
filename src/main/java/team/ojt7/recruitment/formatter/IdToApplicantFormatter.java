package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.service.ApplicantService;
@Component
public class IdToApplicantFormatter implements Formatter<ApplicantDto>{
	@Autowired
	ApplicantService applicantService;
	@Override
	public String print(ApplicantDto object, Locale locale) {
		if (object != null) {
			return object.getName();
		}
		return null;
	}

		@Override
		public ApplicantDto parse(String text, Locale locale) throws ParseException {
			if (StringUtils.hasLength(text)) {
				Long id = Long.parseLong(text);
				return applicantService.findById(id).orElse(null);
			}
			return null;
		}

	}


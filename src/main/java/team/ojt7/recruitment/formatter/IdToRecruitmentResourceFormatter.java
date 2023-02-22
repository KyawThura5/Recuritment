package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;

@Component
public class IdToRecruitmentResourceFormatter implements Formatter<RecruitmentResourceDto> {

	@Autowired
	@Qualifier("RecruitmentResource")
	private RecruitmentResourceService recruitmentResourceService;
	
	@Override
	public String print(RecruitmentResourceDto object, Locale locale) {
		if (object != null) {
			return object.getName();
		}
		return null;
	}

	@Override
	public RecruitmentResourceDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return recruitmentResourceService.findById(id).orElse(null);
		}
		return null;
	}

}

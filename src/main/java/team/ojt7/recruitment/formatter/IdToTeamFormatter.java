package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.service.TeamService;

@Component
public class IdToTeamFormatter implements Formatter<TeamDto> {
	
	@Autowired
	private TeamService teamService;

	@Override
	public String print(TeamDto object, Locale locale) {
		if (object != null) {
			return object.getName();
		}
		return null;
	}

	@Override
	public TeamDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return teamService.findById(id).orElse(null);
		}
		return null;
	}

}

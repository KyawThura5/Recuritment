package team.ojt7.recruitment.model.validator;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;

@Component
public class VacancyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(VacancyDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		VacancyDto vacancy = (VacancyDto) target;
		
		List<RequirePositionDto> rpList = vacancy.getRequirePositions();
		
		
		
		if (rpList != null) {
			Iterator<RequirePositionDto> iterator = rpList.iterator();
			
			while (iterator.hasNext()) {
				RequirePositionDto rp = iterator.next();
				if (rp.getId() != null && rp.getId() == -1) {
					iterator.remove();
				}
			}
			
			for (int i = 0; i < rpList.size(); i++) {
				RequirePositionDto rp = rpList.get(i);
				if (rp.getPosition() == null) {
					errors.rejectValue("requirePositions[%d].position".formatted(i), "notNull", "Select a position");
				}
				
				if (rp.getCount() == null) {
					errors.rejectValue("requirePositions[%d].count".formatted(i), "notNull", "Enter the require count");
				}
				
				if (rp.getTeam() == null) {
					errors.rejectValue("requirePositions[%d].team".formatted(i), "notNull", "Select a team");
				}
			}
		}
		
		if (rpList == null || rpList.isEmpty()) {
			errors.rejectValue("requirePositions", "notEmpty", "Add a position");
			return;
		}
		
		
	}

}

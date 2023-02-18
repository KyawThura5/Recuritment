package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.service.DepartmentService;

@Component
public class IdToDepartmentFormatter implements Formatter<DepartmentDto> {
	
	@Autowired
	private DepartmentService departmentService;

	@Override
	public String print(DepartmentDto object, Locale locale) {
		if (object != null) {
			return object.getName();
		}
		return null;
	}

	@Override
	public DepartmentDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			Long id = Long.parseLong(text);
			return departmentService.findById(id).orElse(null);
		}
		return null;
	}

}

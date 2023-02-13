package team.ojt7.recruitment.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.service.DepartmentService;

@Component("departmentFormatter")
public class IdToDepartmentFormatter implements Formatter<DepartmentDto> {
	
	@Autowired
	private DepartmentService departmentService;

	@Override
	public String print(DepartmentDto object, Locale locale) {
		if (object == null) {
			return null;
		}
		return object.getName();
	}

	@Override
	public DepartmentDto parse(String text, Locale locale) throws ParseException {
		if (StringUtils.hasLength(text)) {
			DepartmentDto department = departmentService.findById(Long.parseLong(text)).orElse(null);
			return department;
		}
		return null;
	}

}

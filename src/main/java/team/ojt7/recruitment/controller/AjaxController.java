package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.service.DepartmentService;

@Controller
public class AjaxController {
	
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/ajax")
	public String showPage(ModelMap model) {
		List<DepartmentDto> departments = departmentService.findAll();
		model.put("departments", departments);
		return "ajax";
	}
}

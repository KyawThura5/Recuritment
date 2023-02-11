package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.service.DepartmentService;

@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/user/department/search")
	public String searchDepartments() {
		return "departments";
	}
	
	@GetMapping("/admin/department/edit")
	public String editDepartment(
			@RequestParam(required = false)
			Long id) {
		return null;
	}
	
	@PostMapping("/admin/department/save")
	public String saveDepartment() {
		return null;
	}
	
	@PostMapping("/admin/department/delete")
	public String deleteDepartment(
			@RequestParam
			Long id
			) {
		return null;
	}
}

package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value = "/admin/department/add", method = RequestMethod.GET)
	public String addNewDepartment(ModelMap model) {
		model.addAttribute("department",new DepartmentDto());		
		return "edit-department";
	}

	@GetMapping("/admin/department/search")
	public String searchDepartments(@RequestParam(required = false) String keyword, ModelMap model) {
		List<DepartmentDto> list = departmentService.search(keyword);
		model.addAttribute("list", list);
		return "departments";
	}

	@GetMapping("/admin/department/edit")
	public String editDepartment(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		DepartmentDto departmentDto = departmentService.findById(id).orElse(new DepartmentDto());
		model.put("department", departmentDto);
		return "edit-department";
	}

	@PostMapping("/admin/department/save")
	public String saveDepartment(@ModelAttribute("department") DepartmentDto dto,BindingResult bs,ModelMap model) {
		if(bs.hasErrors()) {
			return "edit-department";
		}
		
		departmentService.save(DepartmentDto.parse(dto));
		return "redirect:/admin/department/search";
	}

	@GetMapping("/admin/department/delete")
	public String deleteDepartment(@RequestParam("id") Long id) {
		
		departmentService.deleteById(id);
		return "redirect:/admin/department/search";
	}
}

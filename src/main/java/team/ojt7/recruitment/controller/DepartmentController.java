package team.ojt7.recruitment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.DepartmentSearch;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/department/search")
	public String searchDepartments(
			@ModelAttribute("departmentSearch")
			DepartmentSearch departmentSearch,
			ModelMap model) {
		Page<DepartmentDto> page = departmentService.search(departmentSearch);
		model.put("departmentPage", page);
		model.put("departmentSearch", departmentSearch);
		return "departments";
	}
	@GetMapping("/department/search/content")
	public String searchDepartmentContents(
			@ModelAttribute("departmentSearch")
			DepartmentSearch departmentSearch,
			ModelMap model) {
		Page<DepartmentDto> page = departmentService.search(departmentSearch);
		model.put("departmentPage", page);
		model.put("departmentSearch", departmentSearch);
		return "departments-content";
	}


	@GetMapping("/department/edit")
	public String editDepartment(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		DepartmentDto departmentDto = departmentService.findById(id).orElse(new DepartmentDto());
		model.put("department", departmentDto);
		String title = departmentDto.getId() == null ? "Add New Department" : "Edit Department";
		model.put("title", title);
		return "edit-department";
	}

	@PostMapping("/department/save")
	public String saveDepartment(
			@ModelAttribute("department")
			@Validated
			DepartmentDto dto,
			BindingResult bs,
			RedirectAttributes redirect,
			ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				departmentService.save(DepartmentDto.parse(dto));
				String message = dto.getId() == null ? "Successfully created a new department." : "Successfully Updated the department";
				String cssClass = dto.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			String title = dto.getId() == null ? "Add New Department" : "Edit Department";
			model.put("title", title);
			return "edit-department";
		}
		
		return "redirect:/department/search";
	}

	@PostMapping("/department/delete")
	public String deleteDepartment(
			@RequestParam("id")
			Long id,
			RedirectAttributes redirect
			) {
		
		departmentService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the department.", "notice-success"));
		return "redirect:/department/search";
	}
	
	
	@GetMapping("/department/data")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDataFormDepartmentEdit(
			@RequestParam(required = false)
			Long id
			) {
		Map<String, Object> data = new HashMap<>();
		DepartmentDto departmentDto = departmentService.findById(id).orElse(new DepartmentDto());
		data.put("department", departmentDto);
		
		String title = departmentDto.getId() == null ? "Add New Department" : "Edit Department";
		data.put("title", title);
		return ResponseEntity.ok(data);
	}
	
	@PostMapping("/department/validate")
	@ResponseBody
	public ResponseEntity<Map<String, String>> validateDepartment(
			@Validated
			@ModelAttribute("department")
			DepartmentDto departmentDto,
			BindingResult bs
			) {
		Map<String, String> validation = new HashMap<>();
		
		try {
			departmentService.checkValidation(departmentDto);
		} catch (InvalidFieldsException e) {
			for (InvalidField invalidField : e.getFields()) {
				bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
			}
		}
		
		if (bs.hasErrors()) {
			for (FieldError error : bs.getFieldErrors()) {
				validation.put(error.getField(), error.getDefaultMessage());
			}
		}
		
		return ResponseEntity.ok(validation);
	}
}

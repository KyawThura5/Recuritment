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
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.service.PositionService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class PositionController {
	
	@Autowired
	private PositionService positionService;


	@GetMapping("/position/search")
	public String searchPositions(@RequestParam(required = false) String keyword, ModelMap model) {
		List<PositionDto> list = positionService.search(keyword);
		model.addAttribute("list", list);
		return "positions";
	}

	@GetMapping("/position/edit")
	public String editPosition(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		PositionDto positionDto = positionService.findById(id).orElse(new PositionDto());
		model.put("position", positionDto);
		String title = positionDto.getId() == null ? "Add New Position" : "Edit Position";
		model.put("title", title);
		return "edit-position";
	}

	@PostMapping("/position/save")
	public String savePosition(@Validated @ModelAttribute("position") PositionDto dto,BindingResult bs,ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				positionService.save(PositionDto.parse(dto));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			String title = dto.getId() == null ? "Add New Positin" : "Edit Position";
			model.put("title", title);
			return "edit-position";
		}
		
		return "redirect:/position/search";
	}

	@PostMapping("/position/delete")
	public String deletePosition(@RequestParam("id") Long id) {
		
		positionService.deleteById(id);
		return "redirect:/position/search";
	}


}

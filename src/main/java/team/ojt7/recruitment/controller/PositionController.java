package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import team.ojt7.recruitment.model.dto.Alert;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.PositionSearch;
import team.ojt7.recruitment.model.service.PositionService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class PositionController {
	
	@Autowired
	private PositionService positionService;


	@GetMapping("/position/search")
	public String searchPositions(
			@ModelAttribute("positionSearch")
			PositionSearch positionSearch,
			ModelMap model) {
		Page<PositionDto> positionPage = positionService.search(positionSearch);
		model.put("positionPage", positionPage);
		model.put("positionSearch", positionSearch);
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
	public String savePosition(
			@Validated
			@ModelAttribute("position")
			PositionDto dto,
			BindingResult bs,
			RedirectAttributes redirect,
			ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				positionService.save(PositionDto.parse(dto));
				String message = dto.getId() == null ? "Successfully created a new position" : "Successfully updated the position";
				String cssClass = dto.getId() == null ? "notice-success" : "notice-info";
				redirect.addFlashAttribute("alert", new Alert(message, cssClass));
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
	public String deletePosition(
			@RequestParam("id") 
			Long id,
			RedirectAttributes redirect) {
		
		positionService.deleteById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully deleted the position", "notice-success"));
		return "redirect:/position/search";
	}


}

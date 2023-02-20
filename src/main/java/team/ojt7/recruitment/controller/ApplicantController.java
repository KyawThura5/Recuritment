package team.ojt7.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicantController {
	

	@RequestMapping(value = "/hr/applicant/edit", method = RequestMethod.GET)
	public String addNewApplicant(ModelMap model) {
		return null;
	}

	@RequestMapping(value="/hr/applicant/save",method=RequestMethod.POST)
	public String saveApplicant() {
		return null;
		
	}

	@RequestMapping(value = "/hr/applicant/delete", method = RequestMethod.GET)
	public String deleteApplicant(@RequestParam("id")Long id) {
		return null;
	}

	@RequestMapping(value = "/hr/applicant/search", method = RequestMethod.GET)
	public String searchApplicant() {		
		return null;

	}

}
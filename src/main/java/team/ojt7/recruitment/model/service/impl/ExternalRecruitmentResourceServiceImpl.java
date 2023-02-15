package team.ojt7.recruitment.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.ExternalRecruitmentResourceService;
import team.ojt7.recruitment.util.generator.RecruitmentResourceCodeGenerator;

@Service("external")
public class ExternalRecruitmentResourceServiceImpl extends RecruitmentResourceServiceImpl implements ExternalRecruitmentResourceService {

	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;
	
	@Autowired
	private RecruitmentResourceCodeGenerator recruitmentResourceCodeGenerator;
	
	public ExternalRecruitmentResourceDto generateNewWithCode() {
		Long maxId = recruitmentResourceRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		ExternalRecruitmentResourceDto dto = new ExternalRecruitmentResourceDto();
		dto.setCode(recruitmentResourceCodeGenerator.generate(id));
		return dto;
	}
}

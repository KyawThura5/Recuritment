package team.ojt7.recruitment.model.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.DirectRecruitmentResourceDto;
import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.DirectRecruitmentResourceService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.RecruitmentResourceCodeGenerator;

@Service("direct")
public class DirectRecruitmentResourceServiceImpl extends RecruitmentResourceServiceImpl implements DirectRecruitmentResourceService {

	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;
	
	@Autowired
	private RecruitmentResourceCodeGenerator recruitmentResourceCodeGenerator;
	
	public DirectRecruitmentResourceDto generateNewWithCode() {
		Long maxId = recruitmentResourceRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		DirectRecruitmentResourceDto dto = new DirectRecruitmentResourceDto();
		dto.setCode(recruitmentResourceCodeGenerator.generate(id));
		return dto;
	}
	@Override
	public void checkValidation(DirectRecruitmentResourceDto directRecruitmentResource) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		RecruitmentResource duplicatedEntry = recruitmentResourceRepo.findByCodeAndIsDeleted(directRecruitmentResource.getCode(),false);
		if (duplicatedEntry != null && !Objects.equals(directRecruitmentResource.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("code", "duplicated", "A directResource with this code already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
	}
}

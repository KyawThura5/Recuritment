package team.ojt7.recruitment.model.service;

import team.ojt7.recruitment.model.dto.ExternalRecruitmentResourceDto;

public interface ExternalRecruitmentResourceService extends RecruitmentResourceService {

	ExternalRecruitmentResourceDto generateNewWithCode();
}

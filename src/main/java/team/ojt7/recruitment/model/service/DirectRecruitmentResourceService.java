package team.ojt7.recruitment.model.service;

import team.ojt7.recruitment.model.dto.DirectRecruitmentResourceDto;

public interface DirectRecruitmentResourceService extends RecruitmentResourceService {

	DirectRecruitmentResourceDto generateNewWithCode();
}

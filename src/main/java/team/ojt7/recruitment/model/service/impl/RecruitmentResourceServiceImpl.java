package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;

@Service
public class RecruitmentResourceServiceImpl implements RecruitmentResourceService {

	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;
	
	@Override
	public List<RecruitmentResourceDto> search(String keyword) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		return RecruitmentResourceDto.ofList(recruitmentResourceRepo.search(keyword));
	}

	@Override
	public Optional<RecruitmentResourceDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		return Optional.ofNullable(RecruitmentResourceDto.of(recruitmentResourceRepo.findById(id).orElse(null)));
	}

	@Override
	public boolean deleteById(Long id) {
		recruitmentResourceRepo.deleteById(id);
		return true;
	}

	@Override
	public RecruitmentResourceDto save(RecruitmentResource rr) {
		return RecruitmentResourceDto.of(recruitmentResourceRepo.save(rr));
	}

}
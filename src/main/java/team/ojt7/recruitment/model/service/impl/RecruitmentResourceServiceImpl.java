package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceSearch;
import team.ojt7.recruitment.model.entity.RecruitmentResource;
import team.ojt7.recruitment.model.repo.RecruitmentResourceRepo;
import team.ojt7.recruitment.model.service.RecruitmentResourceService;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.RecruitmentResourceCodeGenerator;

@Service("RecruitmentResource")
public class RecruitmentResourceServiceImpl implements RecruitmentResourceService {

	@Autowired
	private RecruitmentResourceRepo recruitmentResourceRepo;
	
	private RecruitmentResourceCodeGenerator recruitmentResourceCodeGenerator;
	
	@Override
	public List<RecruitmentResourceDto> search(String keyword) {
		return search(keyword, null);
	}
	
	@Override
	public List<RecruitmentResourceDto> search(String keyword, String entityType) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		return RecruitmentResourceDto.ofList(recruitmentResourceRepo.search(keyword, entityType));
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
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
//		RecruitmentResource duplicatedEntry = recruitmentResourceRepo.findByNameAndIsDeleted(rr.getName(), false);
//		if (duplicatedEntry != null && !Objects.equals(rr.getId(), duplicatedEntry.getId())) {
//			
//			invalidFieldsException.addField(new InvalidField("name", "duplicated" ,"A recruitment resource with this name already exists"));
//		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		return RecruitmentResourceDto.of(recruitmentResourceRepo.save(rr));
	}

	@Override
	public RecruitmentResourceDto generateNewWithCode() {
		Long maxId = recruitmentResourceRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		RecruitmentResourceDto dto = new RecruitmentResourceDto();
		dto.setCode(recruitmentResourceCodeGenerator.generate(id));
		return dto;
	}

	@Override
	public List<RecruitmentResourceDto> findAllByIsDeleted(boolean isDeleted) {
		return RecruitmentResourceDto.ofList(recruitmentResourceRepo.findAllByIsDeleted(isDeleted));
	}
	
	@Override
	public List<RecruitmentResourceDto> findAllForApplicant(ApplicantDto applicant) {
		Objects.requireNonNull(applicant);
		List<RecruitmentResourceDto> dtos = findAllByIsDeleted(false);
		if (applicant.getRecruitmentResource() == null || dtos.contains(applicant.getRecruitmentResource())) {
			return dtos;
		}
		List<RecruitmentResourceDto> newList = new ArrayList<>(dtos);
		newList.add(applicant.getRecruitmentResource());
		return newList;
	}

	@Override
	public Page<RecruitmentResourceDto> search(RecruitmentResourceSearch search) {
		
		Sort sort = Sort.unsorted();
		if (StringUtils.hasLength(search.getSortBy())) {
			sort = Sort.by(search.getSortBy());
			sort = search.getSortDirection() == null || "asc".equals(search.getSortDirection()) ? sort.ascending() : sort.descending();
		}

		
		String keyword = search.getKeyword() == null ? "%%" : "%" + search.getKeyword() + "%";
		String entityType = search.getEntityType();
		Pageable pageable = PageRequest.of(search.getPage() - 1, search.getSize(),sort);

		Page<RecruitmentResource> page = recruitmentResourceRepo.search(keyword, entityType, pageable);
		Page<RecruitmentResourceDto> dtoPage = new PageImpl<>(RecruitmentResourceDto.ofList(page.getContent()), pageable, page.getTotalElements());
		return dtoPage;
	}

}

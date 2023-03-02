package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.dto.RecruitmentResourceSearch;
import team.ojt7.recruitment.model.entity.RecruitmentResource;

@Service
public interface RecruitmentResourceService {
	
	RecruitmentResourceDto save(RecruitmentResource rr);
	
	List<RecruitmentResourceDto> findAllByIsDeleted(boolean isDeleted);
	
	List<RecruitmentResourceDto> findAllForApplicant(ApplicantDto applicant);

	List<RecruitmentResourceDto> search(String keyword);
	
	List<RecruitmentResourceDto> search(String keyword, String entityType);

	Page<RecruitmentResourceDto> search(RecruitmentResourceSearch search);
	
	Optional<RecruitmentResourceDto> findById(Long id);
	
	boolean deleteById(Long id);
	
	RecruitmentResourceDto generateNewWithCode();
}

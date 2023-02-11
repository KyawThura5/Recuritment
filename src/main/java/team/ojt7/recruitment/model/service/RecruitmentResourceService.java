package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.RecruitmentResourceDto;
import team.ojt7.recruitment.model.entity.RecruitmentResource;

@Service
public interface RecruitmentResourceService {
	
	RecruitmentResourceDto save(RecruitmentResource rr);

	List<RecruitmentResourceDto> search(String keyword);
	
	Optional<RecruitmentResourceDto> findById(Long id);
	
	boolean deleteById(Long id);
}

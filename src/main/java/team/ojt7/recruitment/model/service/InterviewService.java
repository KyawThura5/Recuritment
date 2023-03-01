package team.ojt7.recruitment.model.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.entity.Interview;

public interface InterviewService {

	Page<InterviewDto> search(InterviewSearch search);
	
	InterviewDto save(Interview interview);
	
	boolean deleteById(Long id);
	
	InterviewDto generateNewWithCode();
	
	Optional<InterviewDto> findById(Long id);
}

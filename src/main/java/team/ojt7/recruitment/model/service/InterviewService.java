package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.entity.Interview;

public interface InterviewService {

	List<Interview> findAllByIsDeleted(boolean isDelete);

	List<InterviewDto> search(String keyword);
	
	Optional<InterviewDto> findById(Long id);
	
	List<InterviewDto> findAll();
	
	InterviewDto save(Interview interview);
	
	boolean deleteById(Long id);
	
}

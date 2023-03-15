package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewNameSearch;
import team.ojt7.recruitment.model.entity.InterviewName;

public interface InterviewNameService {

	List<InterviewNameDto> findAllByIsDeleted(boolean isDelete);
	
	List<InterviewNameDto> findAllForInterview(InterviewDto interview);

	Page<InterviewNameDto> search(InterviewNameSearch interviewNameSearch);
	
	Optional<InterviewNameDto> findById(Long id);
	
	List<InterviewNameDto> findAll();
	
	InterviewNameDto save(InterviewName interview);
	
	boolean deleteById(Long id);
	void checkValidation(InterviewNameDto interviewName);
	
}

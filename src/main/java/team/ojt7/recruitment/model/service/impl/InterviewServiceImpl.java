package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.repo.InterviewRepo;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Service
public class InterviewServiceImpl implements InterviewService{
 @Autowired
 private InterviewRepo interviewRepo;
	@Override
	public List<InterviewDto> findAllByIsDeleted(boolean isDelete) {
		// TODO Auto-generated method stub
		return InterviewDto.ofList(interviewRepo.findAllByIsDeleted(true));
	}

	@Override
	@Transactional
	public List<InterviewDto> search(String keyword) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<Interview> interviews=interviewRepo.search(keyword);
		return InterviewDto.ofList(interviews);
	}

	@Override
	@Transactional
	public Optional<InterviewDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Interview interview=interviewRepo.findById(id).orElse(null);
		return Optional.ofNullable(InterviewDto.of(interview));
	}

	@Override
	public List<InterviewDto> findAll() {
		List<Interview> interviews = interviewRepo.findAll();
		return InterviewDto.ofList(interviews);
	}

	@Override
	public InterviewDto save(Interview interview) {
InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Interview duplicatedEntry = interviewRepo.findByNameAndIsDeleted(interview.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(interview.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "A interview with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		Interview savedInterview=interviewRepo.save(interview);
		return InterviewDto.of(savedInterview);
	}

	@Override
	public boolean deleteById(Long id) {
		interviewRepo.deleteById(id);
		return true;
	}

}

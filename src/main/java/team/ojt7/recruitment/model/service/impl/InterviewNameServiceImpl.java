package team.ojt7.recruitment.model.service.impl;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewNameSearch;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.InterviewName;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.repo.InterviewNameRepo;
import team.ojt7.recruitment.model.service.InterviewNameService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Service
public class InterviewNameServiceImpl implements InterviewNameService{
 @Autowired
 private InterviewNameRepo interviewNameRepo;
	@Override
	public List<InterviewNameDto> findAllByIsDeleted(boolean isDelete) {
		// TODO Auto-generated method stub
		return InterviewNameDto.ofList(interviewNameRepo.findAllByIsDeleted(false));
	}

	@Override
	@Transactional
	public Page<InterviewNameDto> search(InterviewNameSearch interviewNameSearch) {
		String keyword = interviewNameSearch.getKeyword() == null ? "%%" : "%" + interviewNameSearch.getKeyword() + "%";
		Pageable pageable = PageRequest.of(interviewNameSearch.getPage() - 1, interviewNameSearch.getSize(),interviewNameSearch.getSort().getSort());
		
		Page<InterviewName> interviewNamePage = interviewNameRepo.search(keyword, pageable);
		
		Page<InterviewNameDto> interviewNameDtoPage = new PageImpl<>(
												InterviewNameDto.ofList(interviewNamePage.getContent()),
												pageable,
												interviewNamePage.getTotalElements()
											);
		return interviewNameDtoPage;
	}

	@Override
	@Transactional
	public Optional<InterviewNameDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		InterviewName interview=interviewNameRepo.findById(id).orElse(null);
		return Optional.ofNullable(InterviewNameDto.of(interview));
	}

	@Override
	public List<InterviewNameDto> findAll() {
		List<InterviewName> interviews = interviewNameRepo.findAll();
		return InterviewNameDto.ofList(interviews);
	}

	@Override
	public InterviewNameDto save(InterviewName interview) {
InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		InterviewName duplicatedEntry = interviewNameRepo.findByNameAndIsDeleted(interview.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(interview.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "An interview with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		InterviewName savedInterview=interviewNameRepo.save(interview);
		return InterviewNameDto.of(savedInterview);
	}

	@Override
	public boolean deleteById(Long id) {
		interviewNameRepo.deleteById(id);
		return true;
	}
	@Override
	public void checkValidation(InterviewNameDto interviewName) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		InterviewName duplicatedEntry = interviewNameRepo.findByNameAndIsDeleted(interviewName.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(interviewName.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "An Interview with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
	}

	@Override
	public List<InterviewNameDto> findAllForInterview(InterviewDto interview) {
		List<InterviewNameDto> interviewNames = findAllByIsDeleted(false);
		if (interview != null && interview.getInterviewName() != null && !interviewNames.contains(interview.getInterviewName()) ) {
			List<InterviewNameDto> newInterviewNames = new LinkedList<>(interviewNames);
			newInterviewNames.add(interview.getInterviewName());
			return newInterviewNames;
		}
		return interviewNames;
	}

}

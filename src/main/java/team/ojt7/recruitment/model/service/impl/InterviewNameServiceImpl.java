package team.ojt7.recruitment.model.service.impl;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.entity.InterviewName;
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
	public List<InterviewNameDto> search(String keyword) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<InterviewName> interviews=interviewNameRepo.search(keyword);
		return InterviewNameDto.ofList(interviews);
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

}

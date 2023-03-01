package team.ojt7.recruitment.model.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.SearchMode;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.repo.InterviewRepo;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.util.generator.InterviewCodeGenerator;

@Service
public class InterviewServiceImpl implements InterviewService {
	
	@Autowired
	private InterviewRepo interviewRepo;
	
	@Autowired
	private InterviewCodeGenerator interviewCodeGenerator;

	@Override
	public Page<InterviewDto> search(InterviewSearch search) {
		String keyword = search.getKeyword() == null ? "%%" : "%" + search.getKeyword() + "%";
		
		Page<Interview> interviews = search.getStatus() == null
									? interviewRepo.search(keyword, search.getDateForm(), search.getDateTo(),search.getInterviewName(),PageRequest.of(search.getPage() - 1, search.getSize()))
									: interviewRepo.search(keyword, search.getDateForm(), search.getDateTo(),search.getStatus(),search.getInterviewName(), PageRequest.of(search.getPage() - 1, search.getSize())); 
		Pageable interviewsPageable = interviews.getPageable();
		Page<InterviewDto> page = new PageImpl<InterviewDto>(InterviewDto.ofList(interviews.getContent()), interviewsPageable, interviews.getTotalElements());
		return page;	
	}

	@Override
	public InterviewDto save(Interview interview) {
		InterviewDto interviewDto = InterviewDto.of(interviewRepo.save(interview));
		return interviewDto;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		interviewRepo.deleteById(id);
		return true;
	}

	@Override
	public InterviewDto generateNewWithCode() {
		Long maxId = interviewRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		InterviewDto interviewDto = new InterviewDto();
		interviewDto.setCode(interviewCodeGenerator.generate(id));
		return interviewDto;
	}

	@Override
	public Optional<InterviewDto> findById(Long id) {
		Interview interview=interviewRepo.findById(id).orElse(null);
		return Optional.ofNullable(InterviewDto.of(interview));
	}
	
	
	
	

}

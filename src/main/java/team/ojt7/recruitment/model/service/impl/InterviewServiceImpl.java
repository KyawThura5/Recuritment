package team.ojt7.recruitment.model.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewNameDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.InterviewName;
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
		
		Pageable pageable = PageRequest.of(search.getPage() - 1, search.getSize());
		InterviewName interviewName = InterviewNameDto.parse(search.getInterviewName());
		
		Page<Interview> interviews =interviewRepo.search(keyword, search.getDateForm(), search.getDateTo(),search.getStatus(), pageable); 
		Pageable interviewsPageable = interviews.getPageable();
		Page<InterviewDto> page = new PageImpl<InterviewDto>(InterviewDto.ofList(interviews.getContent()), interviewsPageable, interviews.getTotalElements());
		return page;	
	}

	@Override
	@Transactional
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
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Interview interview=interviewRepo.findById(id).orElse(null);
		return Optional.ofNullable(InterviewDto.of(interview));
	}
	
	
	
	

}

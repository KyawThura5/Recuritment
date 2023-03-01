package team.ojt7.recruitment.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewSearch;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.repo.InterviewRepo;
import team.ojt7.recruitment.model.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {
	
	@Autowired
	private InterviewRepo interviewRepo;

	@Override
	public Page<InterviewDto> search(InterviewSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterviewDto save(Interview interview) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}

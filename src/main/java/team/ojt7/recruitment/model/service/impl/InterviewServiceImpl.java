package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService{

	@Override
	public List<Interview> findAllByIsDeleted(boolean isDelete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InterviewDto> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InterviewDto> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<InterviewDto> findAll() {
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

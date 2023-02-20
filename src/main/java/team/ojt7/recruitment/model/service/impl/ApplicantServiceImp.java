package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.service.ApplicantService;

public class ApplicantServiceImp implements ApplicantService{

	@Override
	public ApplicantDto save(Applicant applicant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<ApplicantDto> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicantDto generateNewWithCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApplicantDto> search(String keyword, LocalDateTime dateFrom, LocalDateTime dateTo) {
		// TODO Auto-generated method stub
		return null;
	}

}

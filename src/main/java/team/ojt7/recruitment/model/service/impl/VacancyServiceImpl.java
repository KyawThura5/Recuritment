package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.VacancyService;
import team.ojt7.recruitment.util.generator.VacancyCodeGenerator;

@Service
public class VacancyServiceImpl implements VacancyService {
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private VacancyCodeGenerator vacancyCodeGenerator;

	@Override
	public VacancyDto save(Vacancy vacancy) {
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		return false;
	}

	@Override
	public Optional<VacancyDto> findById(Long id) {
		return null;
	}

	@Override
	public VacancyDto generateNewWithCode() {
		return null;
	}

	@Override
	public List<VacancyDto> search(String keyword, Team team, Status status, Position position, LocalDate dateFrom, LocalDate dateTo) {
		return null;
	}

}

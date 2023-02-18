package team.ojt7.recruitment.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

public interface VacancyService {

	VacancyDto save(Vacancy vacancy);
	
	boolean deleteById(Long id);
	
	Optional<VacancyDto> findById(Long id);
	
	VacancyDto generateNewWithCode();
	
	List<VacancyDto> search(String keyword, Team team, Status status, Position position, LocalDate dateFrom, LocalDate dateTo);
}

package team.ojt7.recruitment.model.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Vacancy;

public interface VacancyTestService {
	
	VacancyDto save(Vacancy vacancy);
	
	Optional<VacancyDto> findById(Long id);

	Page<VacancyDto> search(VacancySearch vacancySearch);
	
	VacancyDto generateNewWihCode();
	
	boolean deleteById(Long id);
}

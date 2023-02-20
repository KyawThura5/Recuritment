package team.ojt7.recruitment.model.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;

public interface VacancyTestService {
	
	Optional<VacancyDto> findById(Long id);

	Page<VacancyDto> search(VacancySearch vacancySearch);
	
	VacancyDto generateNewWihCode();
	
	boolean deleteById(Long id);
}

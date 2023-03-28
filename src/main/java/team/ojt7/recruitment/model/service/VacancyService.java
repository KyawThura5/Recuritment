package team.ojt7.recruitment.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Vacancy;

public interface VacancyService {
	
	VacancyDto save(Vacancy vacancy);
	
	Optional<VacancyDto> findById(Long id);

	Page<VacancyDto> search(VacancySearch vacancySearch);
	
	VacancyDto generateNewWihCode();
	
	boolean deleteById(Long id);
	
	List<VacancyDto> findAllForApplicant(ApplicantDto applicantDto);
	
	List<VacancyDto> findAll();
	
	List<VacancyDto> findByCreatedDateRange(LocalDate dateFrom, LocalDate dateTo);
}

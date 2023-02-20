package team.ojt7.recruitment.model.service;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearchCriteria;

public interface VacancyTestService {

	Page<VacancyDto> search(VacancySearchCriteria vacancySearch);
}

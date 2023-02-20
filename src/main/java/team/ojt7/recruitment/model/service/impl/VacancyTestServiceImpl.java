package team.ojt7.recruitment.model.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.repo.VacancyTestRepo;
import team.ojt7.recruitment.model.service.VacancyTestService;
import team.ojt7.recruitment.util.generator.VacancyCodeGenerator;

@Service
public class VacancyTestServiceImpl implements VacancyTestService {
	
	@Autowired
	private VacancyTestRepo vacancyRepo;
	
	@Autowired
	private VacancyCodeGenerator vacancyCodeGenerator;

	@Override
	public Page<VacancyDto> search(VacancySearch vacancySearch) {
		String keyword = vacancySearch.getKeyword() == null ? "%%" : "%" + vacancySearch.getKeyword() + "%";
		
		Page<Vacancy> vacancies = vacancySearch.getStatus() == null ? vacancyRepo.search(keyword, vacancySearch.getDateFrom(), vacancySearch.getDateTo(), PageRequest.of(vacancySearch.getPage() - 1, vacancySearch.getSize())) : vacancyRepo.search(keyword, vacancySearch.getStatus(), vacancySearch.getDateFrom(), vacancySearch.getDateTo(), PageRequest.of(vacancySearch.getPage() - 1, vacancySearch.getSize())); 
		Pageable vacanciesPageable = vacancies.getPageable();
		Page<VacancyDto> page = new PageImpl<VacancyDto>(VacancyDto.ofList(vacancies.getContent()), vacanciesPageable, vacancies.getTotalElements());
		return page;
	}

	@Override
	public Optional<VacancyDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Vacancy vacancy = vacancyRepo.findById(id).orElse(null);
		return Optional.ofNullable(VacancyDto.of(vacancy));
	}

	@Override
	public VacancyDto generateNewWihCode() {
		VacancyDto dto = new VacancyDto();
		Long maxId = vacancyRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		dto.setCode(vacancyCodeGenerator.generate(id));
		dto.setRequirePositions(Collections.emptyList());
		return dto;
	}

	@Override
	public boolean deleteById(Long id) {
		vacancyRepo.deleteById(id);
		return true;
	}

}

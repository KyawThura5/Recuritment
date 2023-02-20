package team.ojt7.recruitment.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.VacancyTestRepo;
import team.ojt7.recruitment.model.service.VacancyTestService;

@Service
public class VacancyTestServiceImpl implements VacancyTestService {
	
	@Autowired
	private VacancyTestRepo vacancyRepo;

	@Override
	public Page<VacancyDto> search(VacancySearch vacancySearch) {
		String keyword = vacancySearch.getKeyword() == null ? "%%" : "%" + vacancySearch.getKeyword() + "%";
		boolean expired = vacancySearch.getStatus() == Status.EXPIRED;
		if (expired) {
			vacancySearch.setStatus(Status.OPENING);
		}
		
		Page<Vacancy> vacancies = vacancyRepo.findByKeyword(keyword, vacancySearch.getStatus(), vacancySearch.getDateFrom(), vacancySearch.getDateTo(), false, PageRequest.of(vacancySearch.getPage() - 1, vacancySearch.getSize())); 
		Pageable vacanciesPageable = vacancies.getPageable();
		Page<VacancyDto> page = new PageImpl<VacancyDto>(VacancyDto.ofList(vacancies.getContent()), vacanciesPageable, vacancies.getTotalElements());
		return page;
	}

}

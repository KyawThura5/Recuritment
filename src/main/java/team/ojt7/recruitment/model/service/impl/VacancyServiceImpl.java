package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.RequirePosition;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.VacancyService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.VacancyCodeGenerator;

@Service
public class VacancyServiceImpl implements VacancyService {
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private VacancyCodeGenerator vacancyCodeGenerator;

	@Override
	public Page<VacancyDto> search(VacancySearch vacancySearch) {
		String keyword = "%%";
		LocalDateTime dateTimeFrom = vacancySearch.getDateFrom() == null ? null : vacancySearch.getDateFrom().atStartOfDay();
		LocalDateTime dateTimeTo = vacancySearch.getDateTo() == null ? null : vacancySearch.getDateTo().plusDays(1).atStartOfDay();
		Page<Vacancy> vacancies = vacancySearch.getStatus() == null
									? vacancyRepo.search(keyword, dateTimeFrom, dateTimeTo, PageRequest.of(vacancySearch.getPage() - 1, vacancySearch.getSize(),vacancySearch.getSort().getSort()))
									: vacancyRepo.search(keyword, vacancySearch.getStatus(), dateTimeFrom, dateTimeTo, PageRequest.of(vacancySearch.getPage() - 1, vacancySearch.getSize(), vacancySearch.getSort().getSort())); 
		Pageable vacanciesPageable = vacancies.getPageable();
		List<Vacancy> vacancyList = vacancies.getContent().stream().filter(
					v -> {
						
						if (StringUtils.hasLength(vacancySearch.getKeyword())) {
							if (v.getCode().toLowerCase().contains(vacancySearch.getKeyword().toLowerCase())) {
								return true;
							}
							
							if (v.getDepartment().getName().toLowerCase().contains(vacancySearch.getKeyword().toLowerCase())) {
								return true;
							}
							
							if (v.getCreatedUser().getName().toLowerCase().contains(vacancySearch.getKeyword().toLowerCase())) {
								return true;
							}
							
							for (RequirePosition rp : v.getRequirePositions()) {
								if (rp.getPosition().getName().toLowerCase().contains(vacancySearch.getKeyword().toLowerCase())) {
									return true;
								}
								if (rp.getTeam().getName().toLowerCase().contains(vacancySearch.getKeyword().toLowerCase())) {
									return true;
								}
							}
							return false;
						}
						return true;
					}
				).toList();
		Page<VacancyDto> page = new PageImpl<VacancyDto>(VacancyDto.ofList(vacancyList), vacanciesPageable, vacancies.getTotalElements());
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

	@Override
	public VacancyDto save(Vacancy vacancy) {
		InvalidFieldsException invalidFiledsException = new InvalidFieldsException();
		
		Vacancy duplicatedEntry = vacancyRepo.findByCodeAndIsDeleted(vacancy.getCode(), false);
		if (duplicatedEntry != null && duplicatedEntry.getId() != vacancy.getId()) {
			invalidFiledsException.addField(new InvalidField("code", "duplicated", "A vacancy with this code already exists."));
		}
		
		if (invalidFiledsException.hasFields()) {
			throw invalidFiledsException;
		}
		
		List<RequirePosition> requirePositions = vacancy.getRequirePositions();
		List<Integer> removeList = new ArrayList<>();
		for (int i = 0; i < requirePositions.size(); i++) {
			RequirePosition ip = requirePositions.get(i);
			for (int j = i + 1; j < requirePositions.size(); j++) {
				RequirePosition jp = requirePositions.get(j);
				if (ip.isEqualsPosition(jp)) {
					if (ip.getId() == null) {
						jp.setCount(jp.getCount() + ip.getCount());
						removeList.add(i);
					} else {
						ip.setCount(ip.getCount() + jp.getCount());
						removeList.add(j);
					}
				}
			}
		}
		
		List<RequirePosition> normalizedRequirePositions = new LinkedList<>(requirePositions);
		for (Integer ri : removeList) {
			normalizedRequirePositions.remove((int) ri);
		}
		vacancy.setRequirePositions(normalizedRequirePositions);
		return VacancyDto.of(vacancyRepo.save(vacancy));
	}
	
	@Override
	public List<VacancyDto> findAllForApplicant(ApplicantDto applicantDto) {
		List<VacancyDto> vacancy = new ArrayList<>(findAllByStatusAndIsDeleted(Status.OPENING, false));
		if (applicantDto.getVacancy() != null && !vacancy.contains(applicantDto.getVacancy())) {
			List<VacancyDto> newVacancyList = new ArrayList<>(vacancy.size() + 1);
			newVacancyList.addAll(vacancy);
			newVacancyList.add(applicantDto.getVacancy());
			return newVacancyList;
		}
		return vacancy;
	}

	public List<VacancyDto> findAllByStatusAndIsDeleted(Status status, boolean isDeleted) {
		return VacancyDto.ofList(vacancyRepo.findAllByStatusAndIsDeleted(status, isDeleted));
	}

	@Override
	public List<VacancyDto> findAll() {
		return VacancyDto.ofList(vacancyRepo.findAll());
	}

	@Override
	public List<VacancyDto> findByCreatedDateRange(LocalDate dateFrom, LocalDate dateTo) {
		LocalDateTime dateTimeFrom = dateFrom == null ? null : dateFrom.atStartOfDay();
		LocalDateTime dateTimeTo = dateTo == null ? null : dateTo.plusDays(1).atStartOfDay();
		return VacancyDto.ofList(vacancyRepo.findByCreatedDateRange(dateTimeFrom, dateTimeTo));
	}

}

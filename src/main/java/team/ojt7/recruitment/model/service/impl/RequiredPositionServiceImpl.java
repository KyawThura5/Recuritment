package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.RequirePositionDetailSearch;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.repo.RequirePositionRepo;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.RequiredPositionService;



@Service
public class RequiredPositionServiceImpl implements RequiredPositionService{
	@Autowired
	private RequirePositionRepo repo;
	
	@Autowired
	private VacancyRepo vacancyRepo;

	public List<RequirePositionDto> findAll() {		
		return RequirePositionDto.ofList(repo.findAll());
	}


	@Override
	public List<RequirePositionDto> findAllByApplicant(ApplicantDto applicant) {
		List<RequirePositionDto> requireposition = new ArrayList<>(findAll());
		if (applicant.getRequirePosition() != null) {
			if (applicant.getRequirePosition() != null && !requireposition.contains(applicant.getRequirePosition())) {
				requireposition.add(applicant.getRequirePosition());
			}
		}
		return requireposition;
	}


	@Override
	public Optional<RequirePositionDto> findById(Long id) {
		RequirePositionDto dto = RequirePositionDto.of(repo.findById(id).orElse(null));
		Long vacancyId = repo.findVacancyId(id);
		Vacancy vacancy = vacancyRepo.findById(vacancyId).orElse(null);
		dto.setVacancy(VacancyDto.of(vacancy));
		return Optional.ofNullable(dto);
	}

	public Optional<RequirePositionDto> findDetail(RequirePositionDetailSearch search) {
		String keyword = search.getKeyword() == null ? "" : search.getKeyword();
		RequirePositionDto requirePosition = findById(search.getId()).orElse(null);
		List<ApplicantDto> applicantList = requirePosition.getApplicants().stream()
				.filter(
						a -> !a.isDeleted() &&
							(search.getStatus() == null || a.getStatus() == search.getStatus()) &&
							(
								a.getCode().toLowerCase().contains(keyword) ||
								a.getName().toLowerCase().contains(keyword) ||
								a.getEmail().toLowerCase().contains(keyword) ||
								a.getPhone().toLowerCase().contains(keyword)
							)
				).toList();
		requirePosition.setApplicants(applicantList);
		return Optional.ofNullable(requirePosition);
	}
	
}

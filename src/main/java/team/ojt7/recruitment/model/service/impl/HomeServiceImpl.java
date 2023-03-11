package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.HomeDto;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.repo.InterviewRepo;
import team.ojt7.recruitment.model.repo.VacancyRepo;
import team.ojt7.recruitment.model.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private VacancyRepo vacancyRepo;
	
	@Autowired
	private InterviewRepo interviewRepo;
	
	@Autowired
	private ApplicantRepo applicantRepo;

	@Override
	public HomeDto getHomeDto() {
		HomeDto home = new HomeDto();
		
		home.setJobOpeningsCount(
				vacancyRepo.findAllByStatusAndIsDeleted(Status.OPENING, false)
					.stream()
					.filter(
							v -> v.getDueDate().isEqual(LocalDate.now()) || v.getDueDate().isAfter(LocalDate.now())
					).toList()
					.size()
				);
		home.setJobExpiringsCount(
				vacancyRepo.findAllByStatusAndIsDeleted(Status.OPENING, false)
					.stream()
					.filter(
							v -> v.getDueDate().isBefore(LocalDate.now())
					).toList()
					.size()
				);
		
		home.setNotStartedInterviewsCount(
					interviewRepo.countByStatus(team.ojt7.recruitment.model.entity.Interview.Status.NOT_START_YET)
				);
		
		home.setNewApplicantsCount(
					applicantRepo.countByStatusAndIsDeleted(team.ojt7.recruitment.model.entity.Applicant.Status.NEW, false)
				);
		
		return home;
	}

}

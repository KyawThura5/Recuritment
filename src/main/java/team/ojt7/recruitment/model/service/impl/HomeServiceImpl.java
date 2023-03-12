package team.ojt7.recruitment.model.service.impl;

import java.time.LocalDate;
import java.util.stream.Collectors;

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
		
		home.setNewInterviewsCountByDate(
					interviewRepo.findAll()
					.stream()
					.filter(
						i -> i.getStatus() == team.ojt7.recruitment.model.entity.Interview.Status.NOT_START_YET && i.getDateTime().getYear() == LocalDate.now().getYear() && i.getDateTime().getMonth() == LocalDate.now().getMonth()
					).collect(
						Collectors.groupingBy(i -> i.getDateTime().getDayOfMonth(), Collectors.summingInt(i -> 1))
					)
				);
		
		home.setPastInterviewsCountByDate(
				interviewRepo.findAll()
				.stream()
				.filter(
					i -> i.getStatus() != team.ojt7.recruitment.model.entity.Interview.Status.NOT_START_YET && i.getDateTime().getYear() == LocalDate.now().getYear() && i.getDateTime().getMonth() == LocalDate.now().getMonth()
				).collect(
					Collectors.groupingBy(i -> i.getDateTime().getDayOfMonth(), Collectors.summingInt(i -> 1))
				)
			);
		
		return home;
	}

}

package team.ojt7.recruitment.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.VacancyService;

@SpringBootTest
public class ReportTest {
	
	@Autowired
	private VacancyService vacancyService;
	
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private ApplicantRepo applicantRepo;

	@Test
	@Transactional
	public void test() {
		
		for (var app : applicantRepo.search("%%", null, LocalDateTime.of(2022, 12, 1, 0, 0, 0), LocalDateTime.of(2023, 12, 1, 0, 0, 0), null)) {
			System.out.println(app.getName());
		}
		
		List<VacancyDto> vacancies = vacancyService.findAll();
		List<ApplicantDto> applicants = applicantService.findAll();
		
		var appliedApplicants = applicants.stream().collect(Collectors.groupingBy(
					a -> a.getRequirePosition().getPosition(),
					Collectors.counting()
				));
		
		var hiredApplicants = applicants.stream().filter(a -> a.getStatus() == Status.HIRED).collect(Collectors.groupingBy(
				a -> a.getRequirePosition().getPosition(),
				Collectors.counting()
			));
		
		vacancies.stream().flatMap(v -> v.getRequirePositions().stream())
		.collect(Collectors.groupingBy(RequirePositionDto::getPosition)).forEach((i, j) -> {
			System.out.println(i.getName());
		});
		var data = vacancies.stream().flatMap(v -> v.getRequirePositions().stream())
			.collect(Collectors.groupingBy(
					RequirePositionDto::getPosition,
					Collectors.reducing(new CandidateCountInfo(), i -> {
						CandidateCountInfo info = new CandidateCountInfo();
						info.setPost((long)i.getCount());
						info.setApplied(appliedApplicants.get(i.getPosition()));
						info.setHired(hiredApplicants.get(i.getPosition()));
						return info;
					}, (i1, i2) -> {
						if (i1 != null && i1.getPost() != null) {
							i2.setPost(i2.getPost() + i1.getPost());
						}
						return i2;
					})
			)
		);
	
		for (var test : data.entrySet()) {
			System.out.printf("%-30s%4d%4d%4d%n", test.getKey().getName(), test.getValue().getPost(), test.getValue().getApplied(), test.getValue().getHired());
		}
	}
}

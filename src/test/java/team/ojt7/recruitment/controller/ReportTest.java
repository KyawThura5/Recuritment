package team.ojt7.recruitment.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.InterviewDto;
import team.ojt7.recruitment.model.dto.InterviewStageInfoByPosition;
import team.ojt7.recruitment.model.dto.InterviewStageInfoDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Applicant.Status;
import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.repo.ApplicantRepo;
import team.ojt7.recruitment.model.service.ApplicantService;
import team.ojt7.recruitment.model.service.InterviewService;
import team.ojt7.recruitment.model.service.VacancyService;

@SpringBootTest
public class ReportTest {
	
	@Autowired
	private VacancyService vacancyService;
	
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private ApplicantRepo applicantRepo;

	@Autowired
	private InterviewService interviewService;
	
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
	
	@Test
	@Transactional
	public void test2() {
		List<InterviewDto> interviews = interviewService.findAll();
		
		var result = interviews.stream().collect(
					Collectors.groupingBy(
						i -> i.getInterviewName(),
						Collectors.reducing(
							new InterviewStageInfoDto(),
							i -> {
								InterviewStageInfoDto iDto = new InterviewStageInfoDto();
								
								Map<PositionDto, InterviewStageInfoByPosition> map = new HashMap<>();
								InterviewStageInfoByPosition iDtoByPosition = new InterviewStageInfoByPosition();
								iDtoByPosition.setTotal(1L);
								
								iDto.setTotal(1L);
								if (i.getStatus() == Interview.Status.PASSED) {
									iDto.setPassed(1L);
									iDtoByPosition.setPassed(1L);
								} else {
									iDto.setFailed(1L);
									iDtoByPosition.setFailed(1L);
								}
								
								map.put(i.getApplicant().getRequirePosition().getPosition(), iDtoByPosition);
								iDto.setStagesByPosition(map);
								return iDto;
							},
							(i1 , i2) -> {
								if (i1 != null) {
									i2.setTotal(i1.getTotal() + i2.getTotal());
									i2.setPassed(i1.getPassed() + i2.getPassed());
									i2.setFailed(i1.getFailed() + i2.getFailed());
									
									if (i1.getStagesByPosition() != null) {
										Map<PositionDto, InterviewStageInfoByPosition> map1 = i1.getStagesByPosition();
										Map<PositionDto, InterviewStageInfoByPosition> map2 = i2.getStagesByPosition();
										
										for (Entry<PositionDto, InterviewStageInfoByPosition> entry : map1.entrySet()) {
											if (map2.get(entry.getKey()) == null) {
												map2.put(entry.getKey(), entry.getValue());
											} else {
												InterviewStageInfoByPosition is1 = entry.getValue();
												InterviewStageInfoByPosition is2 = map2.get(entry.getKey());
												is2.setTotal(is2.getTotal() + is1.getTotal());
												is2.setPassed(is2.getPassed() + is1.getPassed());
												is2.setFailed(is2.getFailed() + is1.getFailed());
											}
										}
									}
									
									
									
								}
								return i2;
							}
						)
					)
				);
		
		System.out.printf("%n%n%n");
		for (var ent : result.entrySet()) {
			System.out.printf("%s:%d:%d:%d%n", ent.getKey().getName(), ent.getValue().getTotal(), ent.getValue().getPassed(), ent.getValue().getFailed());
			System.out.println();
			for (var ent2 : ent.getValue().getStagesByPosition().entrySet()) {
				System.out.printf("%s:%d:%d:%d%n", ent2.getKey().getName(), ent2.getValue().getTotal(), ent2.getValue().getPassed(), ent2.getValue().getFailed());
			}
			System.out.printf("%n%n%n");
		}
	}
}

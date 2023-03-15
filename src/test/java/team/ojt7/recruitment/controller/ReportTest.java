package team.ojt7.recruitment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.CandidateCountInfo;
import team.ojt7.recruitment.model.dto.RequirePositionDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.service.VacancyService;

@SpringBootTest
public class ReportTest {
	
	@Autowired
	private VacancyService vacancyService;

	@Test
	@Transactional
	public void test() {
		List<VacancyDto> vacancies = vacancyService.findAll();
		
		vacancies.stream().flatMap(v -> v.getRequirePositions().stream())
		.collect(Collectors.groupingBy(RequirePositionDto::getPosition)).forEach((i, j) -> {
			System.out.println(i.getName());
		});
		var data = vacancies.stream().flatMap(v -> v.getRequirePositions().stream())
			.collect(Collectors.groupingBy(
					RequirePositionDto::getPosition,
					Collectors.reducing(new CandidateCountInfo(), i -> {
						CandidateCountInfo info = new CandidateCountInfo();
						info.setPost(i.getCount());
						return info;
					}, (i1, i2) -> {
						return i2;
					})
//					Collectors.mapping(
//							i -> new CandidateCountInfo(),
//							Collectors.summingInt(i -> 1)
//					)
			)
		);
		System.out.println(data);
	}
}

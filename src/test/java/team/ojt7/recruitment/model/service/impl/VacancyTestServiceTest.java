package team.ojt7.recruitment.model.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.service.VacancyTestService;

@SpringBootTest
public class VacancyTestServiceTest {

	@Autowired
	private VacancyTestService service;
	
	@Test
	@Transactional
	public void test() {
		Page<VacancyDto> page = service.search(null, PageRequest.of(0, 2));
		System.out.println("Page number : " + page.getNumber());
		System.out.println("Number of elements : " + page.getNumberOfElements());
		System.out.println("Size" + page.getSize());
		System.out.println("Total elements : " + page.getTotalElements());
		System.out.println("Total page" + page.getTotalPages());
		
		for (VacancyDto dto : page.getContent()) {
			System.out.println(dto.getCode());
		}
	}
}

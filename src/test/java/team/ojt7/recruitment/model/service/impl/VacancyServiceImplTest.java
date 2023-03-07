package team.ojt7.recruitment.model.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.dto.VacancySearch;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.RequirePosition;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;
import team.ojt7.recruitment.model.repo.VacancyRepo;

@SpringBootTest
class VacancyServiceImplTest {
	
	@InjectMocks
	VacancyServiceImpl vacancyServiceImpl;
	
	@Mock
	VacancyRepo vacancyTestRepo;
	
	@Mock
	VacancySearch vacancySearch;

	//@Disabled
	@Test
	void testSearch() {
		
		
		List<RequirePosition> positions=new ArrayList<>();
		RequirePosition requirePosition= new RequirePosition();
		Position position=new Position();
		position.setId(1L);
		position.setName("Junior Developer");
//		position.setId(2L);
//		position.setName("Software Engineer");
		
		Team team = new Team();
		team.setId(1L);
		team.setName("ABC");
		
		Department department = new Department();
		department.setId(1L);
		department.setName("Dep1");
		
		User createdUser = new User();
		createdUser.setId(1L);
		createdUser.setName("SuSu");
		
		requirePosition.setId(1L);
		requirePosition.setPosition(position);
		requirePosition.setTeam(team);
		requirePosition.setCount(2);
		
		positions.add(requirePosition);
		
		LocalDateTime dateTime=LocalDateTime.now();
		LocalDate dueDate=LocalDate.of(2023, 04, 22);
		
		Vacancy vacancy = new Vacancy();
		vacancy.setId(1L);
		vacancy.setCode("1");
		vacancy.setStatus(Status.OPENING);
		vacancy.setRequirePositions(positions);
		vacancy.setDepartment(department);
		vacancy.setCreatedUser(createdUser);
		vacancy.setCreatedDateTime(dateTime);
		vacancy.setDueDate(dueDate);
		vacancy.setDeleted(false);
				
		
		vacancySearch.setKeyword(vacancy.getCode());
		vacancySearch.setStatus(vacancy.getStatus());
		vacancySearch.setDateFrom(vacancy.getCreatedDateTime());
		vacancySearch.setDateTo(vacancy.getDueDate());
		
		
		
		Page<Vacancy> vacancies = vacancyTestRepo.search("%1%", Status.OPENING, LocalDateTime.now(), LocalDate.of(2023, 04, 22), PageRequest.of(1,10)); 
		
		when(vacancyTestRepo.search("%1%", Status.OPENING, LocalDateTime.now(), LocalDate.of(2023, 04, 22),PageRequest.of(1,10))).thenReturn(vacancies);
		assertThat(vacancies);		
	}

	@Test
	void testFindById() {
		List<RequirePosition> positions=new ArrayList<>();
		RequirePosition requirePosition= new RequirePosition();
		Position position=new Position();
		position.setId(1L);
		position.setName("Junior Developer");
//		position.setId(2L);
//		position.setName("Software Engineer");
		
		Team team = new Team();
		team.setId(1L);
		team.setName("ABC");
		
		Department department = new Department();
		department.setId(1L);
		department.setName("Dep1");
		
		User createdUser = new User();
		createdUser.setId(1L);
		createdUser.setName("SuSu");
		
		requirePosition.setId(1L);
		requirePosition.setPosition(position);
		requirePosition.setTeam(team);
		requirePosition.setCount(2);
		
		positions.add(requirePosition);
		
		LocalDateTime dateTime=LocalDateTime.now();
		LocalDate dueDate=LocalDate.of(2023, 04, 22);
		
		Vacancy vacancy = new Vacancy();
		vacancy.setId(1L);
		vacancy.setCode("1");
		vacancy.setStatus(Status.OPENING);
		vacancy.setRequirePositions(positions);
		vacancy.setDepartment(department);
		vacancy.setCreatedUser(createdUser);
		vacancy.setCreatedDateTime(dateTime);
		vacancy.setDueDate(dueDate);
		vacancy.setDeleted(false);
		
		when(vacancyTestRepo.findById(vacancy.getId())).thenReturn(Optional.of(vacancy));
		Optional<Vacancy> vacancyDto=vacancyTestRepo.findById(vacancy.getId());
		assertEquals(true,vacancyDto.isPresent());

		
	}

	@Test
	void testDeleteById() {
		vacancyTestRepo.deleteById(1L);
		verify(vacancyTestRepo,times(1)).deleteById(1L);
	}

	@Test
	void testSave() {
		Vacancy vacancy = new Vacancy();
		
		vacancyTestRepo.save(vacancy);
		verify(vacancyTestRepo,times(1)).save(vacancy);

		
		}

}

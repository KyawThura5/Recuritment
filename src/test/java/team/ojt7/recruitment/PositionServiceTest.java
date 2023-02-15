package team.ojt7.recruitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.PositionRepo;
import team.ojt7.recruitment.model.service.impl.PositionServiceImpl;

@SpringBootTest
public class PositionServiceTest {
	@InjectMocks
	private PositionServiceImpl positionService;
	@Mock
	private PositionRepo positionRepo;
     @Test
	public void testSearch() {
		List<Position> resultList=new ArrayList<>();
		Position position1=new Position();
		position1.setId(1L);
		position1.setName("Junior Developer");
		Position position2=new Position();
		position2.setId(2L);
		position2.setName("FullStack Developer");
		resultList.add(position1);
		resultList.add(position2);
		when(positionRepo.search("%Junior Developer%")).thenReturn(resultList);
		List<PositionDto> positions=positionService.search("Junior Developer");
		verify(positionRepo,times(1)).search("%Junior Developer%");

		
	}
     @Test
	public void testFindById() {
	Position position=new Position();
	position.setId(1L);
	position.setName("Junior Developer");
	when(positionRepo.findById(1L)).thenReturn(Optional.of(position));
	PositionDto getPosition=positionService.findById(1L).get();
	assertEquals("Junior Developer",getPosition.getName());

	}
     @Test
	public void testSave() {
		Position position=new Position();
		position.setId(1L);
		position.setName("Junior Developer");
		positionService.save(position);
		verify(positionRepo,times(1)).save(position);

	}
     @Test
	public void testDeleteById() {
    	 positionService.deleteById(1L);
    	 verify(positionRepo,times(1)).deleteById(1L);

	}

}

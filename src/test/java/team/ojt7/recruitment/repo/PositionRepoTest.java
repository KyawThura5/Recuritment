package team.ojt7.recruitment.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.repo.PositionRepo;

@SpringBootTest
public class PositionRepoTest {	

	@Autowired
	private PositionRepo positionRepo;
	
	@ParameterizedTest
	@Sql(scripts = {
		"/truncate-tables.sql",
		"/pre-insert-positions-1.sql"
	})
	@CsvSource({
		"junior,2", "java,1", "php,1", "programmer,2", "senior,0", "manager,3",
		"sale,0", "hiring,1"
	})
	public void testSearchByKeyword(String keyword, int count) {
		List<Position> positions = positionRepo.search("%" + keyword + "%");
		assertEquals(count, positions.size());
	}
	
	@Test
	@Sql(scripts = {
			"/truncate-tables.sql",
			"/pre-insert-positions-1.sql"
		})
	public void testDeleteById() {
		Long id = 1L;
		Position beforeDelete = positionRepo.findById(id).get();
		positionRepo.deleteById(id);
		Position afterDelete = positionRepo.findById(id).get();
		assertFalse(beforeDelete.isDeleted());
		assertTrue(afterDelete.isDeleted());
	}
}

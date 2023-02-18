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

import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.repo.TeamRepo;

@SpringBootTest
public class TeamRepoTest{

	@Autowired
	private TeamRepo teamRepo;
	
	@ParameterizedTest
	@Sql(
		scripts = {
			"/truncate-tables.sql",
			"/pre-insert-teams-1.sql"
		}
	)
	@CsvSource({
		"team,4", "abc,2", "bc,3", "bcd,1", "cd,1", "de,4", "def,1", "efg,0"
	})
	public void testSearchByKeyword(String keyword, int count) {
		List<Team> teams = teamRepo.search("%" + keyword + "%");
		assertEquals(count, teams.size());
	}
	
	@Test
	@Sql(
		scripts = {
			"/truncate-tables.sql",
			"/pre-insert-teams-1.sql"
		}
	)
	public void testDeleteById() {
		Long id = 1L;
		Team beforeDelete = teamRepo.findById(id).get();
		teamRepo.deleteById(id);
		Team afterDelete = teamRepo.findById(id).get();
		assertFalse(beforeDelete.isDeleted());
		assertTrue(afterDelete.isDeleted());
	}
}

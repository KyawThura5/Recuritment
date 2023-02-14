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

import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;

@SpringBootTest
public class DepartmentRepoTest {

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@ParameterizedTest
	@Sql(scripts = {
		"/truncate-tables.sql",
		"/pre-insert-departments-1.sql"
	})
	@CsvSource(
		{
			"java,2", "javascript,1", "script,1", "type,0",
			"cobol,1", "c,2", "python,0"
		}
	)
	public void testSearchByKeyword(String keyword, int count) {
		List<Department> departments = departmentRepo.search("%" + keyword + "%");
		assertEquals(count, departments.size());
	}
	
	@Test
	@Sql(scripts = {
		"/truncate-tables.sql",
		"/pre-insert-departments-1.sql"
	})
	public void testDeleteById() {
		Long id = 1L;
		Department beforeDelete = departmentRepo.findById(id).get();
		departmentRepo.deleteById(id);
		Department afterDelete = departmentRepo.findById(id).get();
		assertFalse(beforeDelete.isDeleted());
		assertTrue(afterDelete.isDeleted());
	}
}

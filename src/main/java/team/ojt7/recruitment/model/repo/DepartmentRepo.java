package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

	@Query("SELECT d FROM Department d WHERE name LIKE :keyword")
	List<Department> search(@Param("keyword") String keyword);
}

package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
	
	List<Department> findAllByIsDeleted(boolean isDeleted);

	Department findByNameAndIsDeleted(String name, boolean isDeleted);
	
	@Query("SELECT d FROM Department d WHERE (name LIKE :keyword) AND (is_deleted = false)")
	List<Department> search(@Param("keyword") String keyword);
	
	@Modifying
	@Query(value = "UPDATE department SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id")Long id);
	@Query(
		value = "SELECT d FROM Department d WHERE (name LIKE :keyword) AND (is_deleted = false)",
		countQuery = "SELECT COUNT(d) FROM Department d WHERE (name LIKE :keyword) AND (is_deleted = false)"
	)
	Page<Department> search(
			@Param("keyword")
			String keyword,
			Pageable pageable
			);
}

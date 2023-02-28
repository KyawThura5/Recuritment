package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Interview;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Long>{
	
	List<Interview> findAllByIsDeleted(boolean isDeleted);
	//@Query("SELECT int FROM Interview int WHERE (name LIKE :keyword) AND (is_deleted = false)")
	Interview findByNameAndIsDeleted(String name, boolean isDeleted);
	
	
	
	@Query("SELECT i FROM Interview i WHERE (name LIKE :keyword) AND (is_deleted = false)")
			List<Interview> search(@Param("keyword") String keyword);
	@Modifying
	@Query(value = "UPDATE interview SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id")Long id);

}

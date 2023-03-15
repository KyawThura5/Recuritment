package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.InterviewName;

@Repository
public interface InterviewNameRepo extends JpaRepository<InterviewName, Long>{
	
	List<InterviewName> findAllByIsDeleted(Boolean isDeleted);
	//@Query("SELECT int FROM Interview int WHERE (name LIKE :keyword) AND (is_deleted = false)")
	InterviewName findByNameAndIsDeleted(String name, boolean isDeleted);
	
	
	
	@Query(
			value = "Select p from InterviewName p where name LIKE :keyword and is_deleted = false",
			countQuery = "Select COUNT(p) from InterviewName p where name LIKE :keyword and is_deleted = false"
		)
		Page<InterviewName> search(
				@Param("keyword")
				String keyword,
				Pageable pageable
				);
	
	
	@Modifying
	@Query(value = "UPDATE interview_name SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id")Long id);

}

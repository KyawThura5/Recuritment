package team.ojt7.recruitment.model.repo;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Long> {
	
	@Query(value="SELECT MAX(id) FROM interview",nativeQuery=true)
	Long findMaxId();
	 
	@Query(
		value=
		"""
				SELECT 	i FROM Interview i WHERE 
				(code LIKE :keyword OR applicant.name LIKE :keyword OR applicant.requirePosition.position.name LIKE :keyword)
				AND(:dateFrom is null OR dateTime >= :dateFrom)
				AND(:dateTo is null OR dateTime <= :dateTo)
				AND(:status is null OR status = :status)
				
		""",
		countQuery=
				"""
						SELECT COUNT(i) FROM Interview i WHERE 
						(code LIKE :keyword OR applicant.name LIKE :keyword OR applicant.requirePosition.position.name LIKE :keyword)
						AND(:dateFrom is null OR dateTime >= :dateFrom)
						AND(:dateTo is null OR dateTime <= :dateTo)
						AND(:status is null OR status = :status)
				"""
	)
	Page<Interview> search(
			@Param("keyword")
			String keyword,
			@Param("dateFrom")
			LocalDateTime dateFrom,
			@Param("dateTo")
			LocalDateTime dateTo,
			@Param("status")
			Status status,
			Pageable pageable
			);

}

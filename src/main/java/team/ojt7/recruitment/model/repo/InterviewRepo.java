package team.ojt7.recruitment.model.repo;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;
import team.ojt7.recruitment.model.entity.InterviewName;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Long> {
	
	@Query(value="SELECT MAX(id) FROM interview",nativeQuery=true)
	Long findMaxId();
	 
	@Query(
			value=
			"""
					SELECT 	i FROM interview i WHERE 
					(code LIKE :keyword OR interviewName LIKE :keyword)
					AND(:dateFrom is null OR datetime >= :dateFrom)
					AND(:dateTo is null OR datetime <= :dateTo)
					AND(:status is null OR status = :status)
					
			""",
			countQuery=
					"""
							SELECT COUNT(i) FROM interview i WHERE 
							(code LIKE :keyword OR interviewName LIKE :keyword)
							AND(:dateFrom is null OR datetime >= :dateFrom)
							AND(:dateTo is null OR datetime <= :dateTo)
							AND(:status is null OR status = :status)
					"""
			
			
			)
	Page<Interview> search(
			@Param("keyword")
			String keyword,
			@Param("dateFrom")
			LocalDate dateFrom,
			@Param("dateTo")
			LocalDate dateTo,
			@Param("status")
			Status status,
			@Param("interviewName")
			InterviewName interivewName,
			Pageable pageable
			);

}

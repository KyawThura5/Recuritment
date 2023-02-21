package team.ojt7.recruitment.model.repo;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.ojt7.recruitment.model.entity.Applicant;

public interface ApplicantRepo extends JpaRepository<Applicant,Long>{
	@Query(value = "SELECT MAX(id) FROM applicant", nativeQuery = true)
	Long findMaxId();

    @Modifying
    @Query(value = "UPDATE Applicant SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id") Long id);
	
    @Query(
    		value = """
    				SELECT a FROM Applicant a where code LIKE :keyword 
    				AND (:dateFrom is null OR createdDate >= :dateFrom) 
    				AND (:dateTo is null OR createdDate <= :dateTo) 
    				AND isDeleted = false
    				ORDER BY createdDate desc
    				""",
    		countQuery = """
    				SELECT COUNT(a) FROM Applicant a where code LIKE :keyword
    				AND (:dateFrom is null OR createdDate >= :dateFrom)
    				AND (:dateTo is null OR createdDate <= :dateTo)
    				AND isDeleted = false
    				ORDER BY createdDate desc
    				"""
    	)
    	Page<Applicant> search(
    			@Param("keyword") String keyword,
    			@Param("dateFrom") LocalDateTime dateFrom,
    			@Param("dateTo") LocalDateTime dateTo,
    			Pageable pageable);
    	
    }

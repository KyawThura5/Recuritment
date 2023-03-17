package team.ojt7.recruitment.model.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.entity.Applicant.Status;

public interface ApplicantRepo extends JpaRepository<Applicant,Long>{
	@Query(value = "SELECT MAX(id) FROM applicant", nativeQuery = true)
	Long findMaxId();
	
	Applicant findByCodeAndIsDeleted(String code, boolean isDeleted);
	
	Long countByStatusAndIsDeleted(Status status, boolean isDeleted);

    @Modifying
    @Query(value = "UPDATE Applicant SET is_deleted = true WHERE id = :id")
	void deleteById(@Param("id") Long id);
    
    @Query("""
    		SELECT a FROM Applicant a WHERE
    		(:dateFrom is null OR hiredDateTime >= :dateFrom)
    		AND (:dateTo is null OR hiredDateTime <= :dateTo)
    		AND isDeleted = false
    		""")
    List<Applicant> findByHiredDateRange(
    		@Param("dateFrom")
    		LocalDateTime dateFrom,
    		@Param("dateTo")
    		LocalDateTime dateTo);
    
    @Query("""
    		SELECT a FROM Applicant a WHERE
    		(:dateFrom is null OR createdDate >= :dateFrom)
    		AND (:dateTo is null OR createdDate <= :dateTo)
    		AND isDeleted = false
    		""")
    List<Applicant> findByCreatedDateRange(
    		@Param("dateFrom")
    		LocalDateTime dateFrom,
    		@Param("dateTo")
    		LocalDateTime dateTo);
    
    @Query(
    		value = """
    		select app from Applicant app where status = :status
    		AND isDeleted = false
    		""",
    		countQuery = """
    				select COUNT(app) from Applicant app where status = :status
    		AND isDeleted = false
    				"""
    		)
    List<Applicant> searchStatusAndResources(@Param("status") Status status);
	
    @Query(
    		value = """
    				SELECT a FROM Applicant a where 
    				(code LIKE :keyword  OR name LIKE :keyword)
    				AND (:status is null OR status = :status)
    				AND (:dateFrom is null OR createdDate >= :dateFrom) 
    				AND (:dateTo is null OR createdDate <= :dateTo) 
    				AND isDeleted = false
    				""",
    		countQuery = """
    				SELECT COUNT(a) FROM Applicant a where
    				(code LIKE :keyword  OR name LIKE :keyword)
    				AND (:status is null OR status = :status)
    				AND (:dateFrom is null OR createdDate >= :dateFrom)
    				AND (:dateTo is null OR createdDate <= :dateTo)
    				AND isDeleted = false
    				"""
    	)
    	Page<Applicant> search(
    			@Param("keyword") String keyword,
    			@Param("status") Status status,
    			@Param("dateFrom") LocalDateTime dateFrom,
    			@Param("dateTo") LocalDateTime dateTo,
    			Pageable pageable);
    	
    }

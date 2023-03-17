package team.ojt7.recruitment.model.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
	
	@Query(value = "SELECT MAX(id) FROM vacancy", nativeQuery = true)
	Long findMaxId();
	
	@Modifying
	@Query("UPDATE Vacancy SET isDeleted = true WHERE id = :id")
	void deleteById(@Param("id") Long id);
	
	Vacancy findByCodeAndIsDeleted(String code, boolean isDeleted);
	
	List<Vacancy> findAllByStatusAndIsDeleted(Status status,boolean isDeleted);
	
	@Query("""
			SELECT v FROM Vacancy v WHERE
			(:dateFrom is null OR createdDateTime >= :dateFrom)
			AND (:dateTo is null OR createdDateTime <= :dateTo)
			AND isDeleted = false
			""")
	List<Vacancy> findByCreatedDateRange(
			@Param("dateFrom")
			LocalDateTime dateFrom,
			@Param("dateTo")
			LocalDateTime dateTo);

	@Query(
		value = """
				SELECT v FROM Vacancy v where code LIKE :keyword 
				AND (:dateFrom is null OR createdDateTime >= :dateFrom) 
				AND (:dateTo is null OR createdDateTime <= :dateTo) 
				AND ((:#{#status.toString()} = 'EXPIRED' AND dueDate < CURDATE() AND status = 'OPENING') OR (:#{#status.toString()} = 'OPENING' AND dueDate >= CURDATE() AND status = 'OPENING') OR (:#{#status.toString()} = 'CLOSED' AND status = 'CLOSED'))
				AND isDeleted = false
				""",
		countQuery = """
				SELECT COUNT(v) FROM Vacancy v where code LIKE :keyword
				AND (:dateFrom is null OR createdDateTime >= :dateFrom)
				AND (:dateTo is null OR createdDateTime <= :dateTo)
				AND ((:#{#status.toString()} = 'EXPIRED' AND dueDate < CURDATE() AND status = 'OPENING') OR (:#{#status.toString()} = 'OPENING' AND dueDate >= CURDATE() AND status = 'OPENING') OR (:#{#status.toString()} = 'CLOSED' AND status = 'CLOSED'))
				AND isDeleted = false
				"""
	)
	Page<Vacancy> search(
			@Param("keyword") String keyword,
			@Param("status") Status status,
			@Param("dateFrom") LocalDateTime dateFrom,
			@Param("dateTo") LocalDateTime dateTo,
			Pageable pageable);
	
	
	@Query(
			value = """
					SELECT v FROM Vacancy v where code LIKE :keyword 
					AND (:dateFrom is null OR createdDateTime >= :dateFrom) 
					AND (:dateTo is null OR createdDateTime <= :dateTo)
					AND isDeleted = false
					""",
			countQuery = """
					SELECT COUNT(v) FROM Vacancy v where code LIKE :keyword
					AND (:dateFrom is null OR createdDateTime >= :dateFrom)
					AND (:dateTo is null OR createdDateTime <= :dateTo)
					AND isDeleted = false
					"""
		)
		Page<Vacancy> search(
				@Param("keyword") String keyword,
				@Param("dateFrom") LocalDateTime dateFrom,
				@Param("dateTo") LocalDateTime dateTo,
				Pageable pageable);
}

package team.ojt7.recruitment.model.repo;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

@Repository
public interface VacancyTestRepo extends JpaRepository<Vacancy, Long> {

	@Query(
		value = """
				SELECT v FROM Vacancy v where code LIKE :keyword 
				AND (:dateFrom is null OR createdDate >= :dateFrom) 
				AND (:dateTo is null OR createdDate <= :dateTo) 
				AND (:status is null OR status = :status)
				AND (:expired = false OR dueDate < CURDATE())
				""",
		countQuery = """
				SELECT COUNT(v) FROM Vacancy v where code LIKE :keyword
				AND (:dateFrom is null OR createdDate >= :dateFrom)
				AND (:dateTo is null OR createdDate <= :dateTo)
				AND (:status is null OR status = :status)
				AND (:expired = false OR dueDate < CURDATE())
				"""
	)
	Page<Vacancy> findByKeyword(
			@Param("keyword") String keyword,
			@Param("status") Status status,
			@Param("dateFrom") LocalDate dateFrom,
			@Param("dateTo") LocalDate dateTo,
			@Param("expired")
			boolean expired,
			Pageable pageable);
}

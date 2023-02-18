package team.ojt7.recruitment.model.repo;

import java.time.LocalDate;
import java.util.List;

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
	
	Vacancy findByCodeAndIsDeleted(String code, boolean isDeleted);
	Vacancy findByIdAndIsDeleted(Long id, boolean isDeleted);
	Vacancy findByCreatedDateAndDueDateAndIsDeleted(LocalDate createdDate,LocalDate dueDate,boolean isDeleted);
	Vacancy findByStatusAndIsDeleted(Status status,boolean isDeleted);

    @Modifying
    @Query(value = "UPDATE vacancy SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id") Long id);
	
    @Query("SELECT v FROM vacancy v WHERE (code LIKE :keyword or created_date like :dateFrom or due_date like :dateTo or v.status like :status) AND (is_deleted = false)")
	List<Vacancy> search(String keyword, Status status, LocalDate dateFrom, LocalDate dateTo);
}

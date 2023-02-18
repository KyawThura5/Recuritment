package team.ojt7.recruitment.model.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.entity.Vacancy;
import team.ojt7.recruitment.model.entity.Vacancy.Status;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
	
	Long findMaxId();
	
	Vacancy findByCodeAndIsDeleted(String code, boolean isDeleted);

	void deleteById(@Param("id") Long id);
	
	List<Vacancy> search(String keyword, Team team, Status status, Position position, LocalDate dateFrom, LocalDate dateTo);
}

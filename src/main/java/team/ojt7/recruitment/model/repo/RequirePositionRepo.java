package team.ojt7.recruitment.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.ojt7.recruitment.model.entity.RequirePosition;

public interface RequirePositionRepo extends JpaRepository<RequirePosition,Long>{

	List<RequirePosition> findAll();
	
	@Query("""
			SELECT r FROM RequirePosition r JOIN Applicant a ON a.requirePosition.id = r.id WHERE
			(r.id = :id) AND
			(a.code LIKE :keyword OR a.name LIKE :keyword OR a.email LIKE :keyword OR a.phone LIKE :keyword)
			""")
	Optional<RequirePosition> findDetailByIdAndKeyword(
			@Param("id")
			Long id,
			@Param("keyword")
			String keyword);
	
	@Query(value = "SELECT vacancy_id FROM require_position WHERE id = :id", nativeQuery = true)
	Long findVacancyId(Long id);

}

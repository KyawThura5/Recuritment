package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
	@Query("SELECT t FROM Team t WHERE name LIKE :keyword")
	List<Team> search(@Param("keyword") String keyword);

}

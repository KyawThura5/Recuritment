package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.ojt7.recruitment.model.entity.Position;

public interface PositionRepo extends JpaRepository<Position,Long>{

	@Query("Select p from Position p where name LIKE :keyword")
	List<Position> search(@Param("keyword") String keyword);

}

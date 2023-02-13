package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.ojt7.recruitment.model.entity.Position;

public interface PositionRepo extends JpaRepository<Position,Long>{

	@Query("Select p from Position p where(name LIKE :keyword) and (is_deleted = false)")
	List<Position> search(@Param("keyword") String keyword);
	
	@Modifying
	@Query(value="UPDATE position SET is_deleted = true WHERE id= :id",nativeQuery=true)
	void deleteById(@Param("id")Long id);
	
}

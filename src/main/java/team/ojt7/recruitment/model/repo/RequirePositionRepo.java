package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team.ojt7.recruitment.model.entity.RequirePosition;

public interface RequirePositionRepo extends JpaRepository<RequirePosition,Long>{

	List<RequirePosition> findAll();

}

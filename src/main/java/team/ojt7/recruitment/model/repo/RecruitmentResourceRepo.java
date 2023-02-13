package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.RecruitmentResource;

@Repository
public interface RecruitmentResourceRepo extends JpaRepository<RecruitmentResource, Long> {

	@Query("SELECT r FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (is_deleted = false)")
	List<RecruitmentResource> search(@Param("keyword") String keyword);
	
	@Query("SELECT r FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (:entityType is null OR entity_type = :entityType) AND (is_deleted = false)")
	List<RecruitmentResource> search(
			@Param("keyword") String keyword,
			@Param("entityType") String entityType
			);
	
	@Modifying
	@Query(value = "UPDATE recruitment_resource SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id")Long id);
}

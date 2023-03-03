package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.ApplicantStatusChangeHistory;

@Repository
public interface ApplicantStatusChangeHistoryRepo extends JpaRepository<ApplicantStatusChangeHistory, Long>  {

	@Query("SELECT h FROM ApplicantStatusChangeHistory h WHERE applicant.id = :applicantId ORDER BY updatedOn DESC")
	List<ApplicantStatusChangeHistory> findByAppliacantId(
			@Param("applicantId")
			Long applicantId);
}

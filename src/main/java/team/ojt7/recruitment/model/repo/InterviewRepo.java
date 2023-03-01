package team.ojt7.recruitment.model.repo;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Interview;
import team.ojt7.recruitment.model.entity.Interview.Status;
import team.ojt7.recruitment.model.entity.InterviewName;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Long> {
	
	Long findMaxId();

	Page<Interview> search(
			@Param("keyword")
			String keyword,
			@Param("dateFrom")
			LocalDate dateFrom,
			@Param("dateTo")
			LocalDate dateTo,
			@Param("status")
			Status status,
			@Param("interviewName")
			InterviewName interivewName,
			Pageable pageable
			);

}

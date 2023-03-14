package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.TeamSearch;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Team;

public interface TeamService {
	List<TeamDto> search(String keyword);
	Optional<TeamDto> findById(Long id);
	TeamDto save(Team team);
	boolean deleteById(Long id);
	List<TeamDto> findAllByIsDeleted(boolean isDeleted);
	List<TeamDto> findAllByDepartmentIdAndIsDeleted(Long id, boolean isDeleted);
	List<TeamDto> findAllForVacancy(VacancyDto vacancy);
	Page<TeamDto> findpage(TeamSearch teamSearch);
	void checkValidation(TeamDto team);
}

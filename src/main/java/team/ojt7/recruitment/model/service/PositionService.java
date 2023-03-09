package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.PositionSearch;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Position;

public interface PositionService {
	List<PositionDto> search(String keyword);

	Page<PositionDto> search(PositionSearch positionSearch);
	
	Optional<PositionDto> findById(Long id);

	PositionDto save(Position position);

	boolean deleteById(Long id);

	List<PositionDto> findAll();

	List<PositionDto> findAllForVacancy(VacancyDto vacancy);

	List<PositionDto> findAllByIsDeleted(boolean isDeleted);
	
	void checkValidation(PositionDto positionDto);


}

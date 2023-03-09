package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.DepartmentSearch;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Department;

public interface DepartmentService {
	
	List<DepartmentDto> findAllByIsDeleted(boolean isDelete);
	
	List<DepartmentDto> findAllForVacancy(VacancyDto vacancy);
	
	List<DepartmentDto> findAllForTeam(TeamDto team);

	List<DepartmentDto> search(String keyword);
	
	Page<DepartmentDto> search(DepartmentSearch departmentSearch);
	
	Optional<DepartmentDto> findById(Long id);
	
	List<DepartmentDto> findAll();
	
	DepartmentDto save(Department department);
	
	boolean deleteById(Long id);
	
	void checkValidation(DepartmentDto departmentDto);

}

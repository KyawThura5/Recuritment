package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;

public interface DepartmentService {

	List<DepartmentDto> search(String keyword);
	
	Optional<DepartmentDto> findById(Long id);
	
	DepartmentDto save(Department department);
	
	boolean deleteById(Long id);
}

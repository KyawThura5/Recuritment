package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Override
	public List<DepartmentDto> search(String keyword) {
		
		return null;
	}

	@Override
	public Optional<DepartmentDto> findById(Long id) {
		
		return Optional.empty();
	}

	@Override
	public DepartmentDto save(Department department) {
		
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		
		return false;
	}

}

package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Override
	@Transactional
	public List<DepartmentDto> search(String keyword) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<Department> departments=departmentRepo.search(keyword);
		return DepartmentDto.ofList(departments);
		
	}

	@Override
	@Transactional
	public Optional<DepartmentDto> findById(Long id) {
		Department department=departmentRepo.findById(id).orElse(null);
		return Optional.ofNullable(DepartmentDto.of(department));
	}

	@Override
	public DepartmentDto save(Department department) {
		Department savedDepartment=departmentRepo.save(department);
		return DepartmentDto.of(savedDepartment);
	}

	@Override
	public boolean deleteById(Long id) {
		departmentRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DepartmentDto> findAll() {
		List<Department> departments = departmentRepo.findAll();
		return DepartmentDto.ofList(departments);
	}

}

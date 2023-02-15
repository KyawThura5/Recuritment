package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.repo.DepartmentRepo;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

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
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Department duplicatedEntry = departmentRepo.findByNameAndIsDeleted(department.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(department.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "A department with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
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

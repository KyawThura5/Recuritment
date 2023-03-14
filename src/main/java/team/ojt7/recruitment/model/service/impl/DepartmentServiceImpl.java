package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.DepartmentSearch;
import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
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
		if (id == null) {
			return Optional.ofNullable(null);
		}
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

	@Override
	public List<DepartmentDto> findAllByIsDeleted(boolean isDelete) {
		return DepartmentDto.ofList(departmentRepo.findAllByIsDeleted(isDelete));
	}

	@Override
	public List<DepartmentDto> findAllForVacancy(VacancyDto vacancy) {
		if (vacancy.getId() != null && vacancy.getDepartment() != null) {
			return List.of(vacancy.getDepartment());
		}
		
		List<DepartmentDto> departments = findAllByIsDeleted(false);
		return departments;
	}

	@Override
	public Page<DepartmentDto> search(DepartmentSearch departmentSearch) {
				
		String keyword = departmentSearch.getKeyword() == null ? "%%" : "%" + departmentSearch.getKeyword() + "%";
		Pageable pageable = PageRequest.of(departmentSearch.getPage() - 1, departmentSearch.getSize(),departmentSearch.getSort().getSort());
		
		Page<Department> departmentPage = departmentRepo.search(keyword, pageable);
		
		Page<DepartmentDto> departmentDtoPage = new PageImpl<>(DepartmentDto.ofList(departmentPage.getContent()), pageable, departmentPage.getTotalElements());
		return departmentDtoPage;
	}

	@Override
	public List<DepartmentDto> findAllForTeam(TeamDto team) {
		List<Department> departments = departmentRepo.findAllByIsDeleted(false);
		List<DepartmentDto> departmentDtos = new ArrayList<>(DepartmentDto.ofList(departments));
		DepartmentDto department = team.getDepartment();
		if (department != null) {
			if (!departmentDtos.contains(department)) {
				departmentDtos.add(department);
			}
		}
		return departmentDtos;
	}
	
	
	@Override
	public void checkValidation(DepartmentDto departmentDto) {
InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Department duplicatedEntry = departmentRepo.findByNameAndIsDeleted(departmentDto.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(departmentDto.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "A department with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
	}

}

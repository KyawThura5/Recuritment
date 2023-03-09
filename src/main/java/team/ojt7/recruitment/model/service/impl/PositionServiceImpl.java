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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.dto.PositionSearch;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Department;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.repo.PositionRepo;
import team.ojt7.recruitment.model.service.PositionService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Service
public class PositionServiceImpl implements PositionService{
	@Autowired
	PositionRepo repo;
	
	@Override
	@Transactional
	public List<PositionDto> search(String keyword) {
		keyword = keyword==null ? "%%" : "%" + keyword + "%";
		List<Position> position=repo.search(keyword);
		return PositionDto.ofList(position);
	}
	
	@Override
	@Transactional
	public Optional<PositionDto> findById(Long id) {
		if (id == null) {
			return Optional.ofNullable(null);
		}
		Position p=repo.findById(id).orElse(null);
		return Optional.ofNullable(PositionDto.of(p));
	}
	
	@Override
	public PositionDto save(Position position) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Position duplicatedEntry = repo.findByNameAndIsDeleted(position.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(position.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "A position with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		Position p=repo.save(position);
		return PositionDto.of(p);
	}
	
	@Override
	public boolean deleteById(Long id) {
		repo.deleteById(id);
		return true;
	}
	
	@Override
	public List<PositionDto> findAll() {
		List<Position> positions = repo.findAll();
		return PositionDto.ofList(positions);
	}
	
	@Override
	public List<PositionDto> findAllForVacancy(VacancyDto vacancy) {
		List<PositionDto> positions = new ArrayList<>(findAllByIsDeleted(false));
		if (vacancy.getRequirePositions() != null) {
			vacancy.getRequirePositions().stream()
				.map(rp -> rp.getPosition())
				.forEach(p -> {
					if (p != null && !positions.contains(p)) {
						positions.add(p);
					}
				});
		}
		return positions;
	}

	@Override
	public List<PositionDto> findAllByIsDeleted(boolean isDeleted) {
		return PositionDto.ofList(repo.findAllByIsDeleted(isDeleted));
	}

	@Override
	public Page<PositionDto> search(PositionSearch positionSearch) {
		String keyword = positionSearch.getKeyword() == null ? "%%" : "%" + positionSearch.getKeyword() + "%";
		Pageable pageable = PageRequest.of(positionSearch.getPage() - 1, positionSearch.getSize());
		
		Page<Position> positionPage = repo.search(keyword, pageable);
		
		Page<PositionDto> positionDtoPage = new PageImpl<>(
												PositionDto.ofList(positionPage.getContent()),
												pageable,
												positionPage.getTotalElements()
											);
		return positionDtoPage;
	}
	
	@Override
	public void checkValidation(PositionDto positionDto) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		Position duplicatedEntry = repo.findByNameAndIsDeleted(positionDto.getName(), false);
		if (duplicatedEntry != null && !Objects.equals(positionDto.getId(), duplicatedEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("name", "duplicated", "A position with this name already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
	}


}

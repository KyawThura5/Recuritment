package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.Position;
import team.ojt7.recruitment.model.repo.PositionRepo;
import team.ojt7.recruitment.model.service.PostionService;

@Service
public class PositionServiceImpl implements PostionService{
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
	Position p=repo.findById(id).orElse(null);
	return Optional.ofNullable(PositionDto.of(p));
}

@Override
public PositionDto save(Position position) {
	Position p=repo.save(position);
	return PositionDto.of(p);
}

@Override
public boolean deleteById(Long id) {
	repo.deleteById(id);
	return true;
}


}

package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.entity.Position;

public interface PostionService {
List<PositionDto> search(String keyword);
Optional<PositionDto>findById(Long id);
PositionDto save(Position position);
boolean deleteById(Long id);
}

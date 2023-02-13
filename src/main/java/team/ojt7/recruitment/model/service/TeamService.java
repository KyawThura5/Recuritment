package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.entity.Team;

public interface TeamService {
 List<TeamDto> search(String keyword);
 Optional<TeamDto> findById(Long id);
 TeamDto save(Team team);
 boolean deleteById(Long id);
 
 
 
 
 

}
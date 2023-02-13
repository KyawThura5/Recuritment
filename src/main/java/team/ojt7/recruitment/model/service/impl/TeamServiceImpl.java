package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import team.ojt7.recruitment.model.dto.TeamDto;

import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.repo.TeamRepo;
import team.ojt7.recruitment.model.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {
@Autowired
private TeamRepo teamRepo;
@Override
@Transactional
public List<TeamDto> search(String keyword) {
	keyword = keyword == null ? "%%" : "%" + keyword + "%";
	List<Team> teams=teamRepo.search(keyword);
	return TeamDto.ofList(teams);
	
}
@Override
@Transactional
public Optional<TeamDto> findById(Long id) {
	Team team=teamRepo.findById(id).orElse(null);
	return Optional.ofNullable(TeamDto.of(team));
}

@Override
public TeamDto save(Team team) {
	Team savedTeam=teamRepo.save(team);
	return TeamDto.of(savedTeam);
}

@Override
public boolean deleteById(Long id) {
	teamRepo.deleteById(id);
	return true;
}
}

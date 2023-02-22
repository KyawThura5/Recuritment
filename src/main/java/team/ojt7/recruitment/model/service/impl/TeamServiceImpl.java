package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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


import team.ojt7.recruitment.model.dto.TeamDto;
import team.ojt7.recruitment.model.dto.VacancyDto;
import team.ojt7.recruitment.model.entity.Team;
import team.ojt7.recruitment.model.repo.TeamRepo;
import team.ojt7.recruitment.model.service.TeamService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

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
	if (id == null) {
		return Optional.ofNullable(null);
	}
	Team team=teamRepo.findById(id).orElse(null);
	return Optional.ofNullable(TeamDto.of(team));
}

@Override
public TeamDto save(Team team) {
	InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
	
	Team duplicatedEntry = teamRepo.findByNameAndDepartmentIdAndIsDeleted(team.getName(), team.getDepartment().getId(), false);
	if (duplicatedEntry != null && !Objects.equals(team.getId(), duplicatedEntry.getId())) {
		invalidFieldsException.addField(new InvalidField("name", "duplicated", "A team with this name already exists in the department"));
	}
	
	if (invalidFieldsException.hasFields()) {
		throw invalidFieldsException;
	}
	Team savedTeam=teamRepo.save(team);
	return TeamDto.of(savedTeam);
}

	@Override
	public boolean deleteById(Long id) {
		teamRepo.deleteById(id);
		return true;
	}
	
	@Override
	public List<TeamDto> findAllByIsDeleted(boolean isDeleted) {
		return TeamDto.ofList(teamRepo.findAllByIsDeleted(isDeleted));
	}
	
	@Override
	public List<TeamDto> findAllForVacancy(VacancyDto vacancy) {
		if (vacancy.getDepartment() == null) {
			return Collections.emptyList();
		}
		List<TeamDto> teams = new ArrayList<>(findAllByDepartmentIdAndIsDeleted(vacancy.getDepartment().getId(), false));
		
		if (vacancy.getDepartment().getTeams() != null) {
			vacancy.getDepartment().getTeams().stream()
				.forEach(t -> {
					if (!teams.contains(t)) {
						teams.add(t);
					}
				});
		}
		
		return teams;
	}
	
	@Override
	public List<TeamDto> findAllByDepartmentIdAndIsDeleted(Long id, boolean isDeleted) {
		return TeamDto.ofList(teamRepo.findAllByDepartmentIdAndIsDeleted(id, isDeleted));
	}
	@Override
	public Page<TeamDto> findpage(String keyword, int page,int size) {
		keyword=keyword==null? "%%" :"%"+keyword+"%";
		Page<Team>teams=teamRepo.searchPage(keyword,PageRequest.of(page-1,size));
		Pageable pageable=teams.getPageable();
		Page<TeamDto>p=new PageImpl<TeamDto>(TeamDto.ofList(teams.getContent()),pageable,teams.getTotalElements());		
		return p;
	}
}

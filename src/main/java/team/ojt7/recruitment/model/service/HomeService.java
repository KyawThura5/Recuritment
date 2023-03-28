package team.ojt7.recruitment.model.service;

import team.ojt7.recruitment.model.dto.AdminHomeDto;
import team.ojt7.recruitment.model.dto.HomeDto;

public interface HomeService {

	HomeDto getHomeDto();
	
	AdminHomeDto getAdminHomeDto();
}

package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public List<UserDto> search(String keyword, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UserDto> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public UserDto save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}

package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;
import team.ojt7.recruitment.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	@Transactional
	public List<UserDto> search(String keyword, Role role) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<User> users=userRepo.search(keyword, role);
		return UserDto.ofList(users);
	}

	@Override
	@Transactional
	public Optional<UserDto> findById(Long id) {
		User user=userRepo.findById(id).orElse(null);
		return Optional.ofNullable(UserDto.of(user));
	}

	@Override
	@Transactional
	public UserDto save(User user) {
		User savedUser = userRepo.save(user);
		return UserDto.of(savedUser);
	}

	@Override
	@Transactional
	public boolean deleteById(Long id) {
		userRepo.deleteById(id);
		return true;
	}

}

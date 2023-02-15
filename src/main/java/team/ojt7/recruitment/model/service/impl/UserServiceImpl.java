package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;
import team.ojt7.recruitment.model.service.UserService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.util.generator.UserCodeGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserCodeGenerator userCodeGenerator;
	
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
		
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		User duplicateEntry = userRepo.findByCodeAndIsDeleted(user.getCode(), false);
		if (duplicateEntry != null && !Objects.equals(user.getId(), duplicateEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("code", "duplicated", "A user with this code already exists"));
		}
		
		duplicateEntry = userRepo.findByEmailAndIsDeleted(user.getEmail(), false);
		if (duplicateEntry != null && !Objects.equals(user.getId(), duplicateEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("email", "duplicated", "A user with this email already exists"));
		}
		
		duplicateEntry = userRepo.findByPhoneAndIsDeleted(user.getPhone(), false);
		if (duplicateEntry != null && !Objects.equals(user.getId(), duplicateEntry.getId())) {
			invalidFieldsException.addField(new InvalidField("phone", "duplicated", "A user with this phone already exists"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		User savedUser = userRepo.save(user);
		return UserDto.of(savedUser);
	}

	@Override
	@Transactional
	public boolean deleteById(Long id) {
		userRepo.deleteById(id);
		return true;
	}

	@Override
	public UserDto generateNewWithCode() {
		Long maxId = userRepo.findMaxId();
		Long id = maxId == null ? 1 : maxId + 1;
		UserDto user = new UserDto();
		user.setCode(userCodeGenerator.generate(id));
		return user;
	}

}

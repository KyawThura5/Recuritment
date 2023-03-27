package team.ojt7.recruitment.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.ojt7.recruitment.event.UserUpdateEvent;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.dto.UserSearch;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.entity.User.Status;
import team.ojt7.recruitment.model.repo.UserRepo;
import team.ojt7.recruitment.model.service.UserService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.model.service.exception.ServiceException;
import team.ojt7.recruitment.util.generator.UserCodeGenerator;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserCodeGenerator userCodeGenerator;
	
	@Autowired
	private HttpSession session;
	
	@Override
	@Transactional
	public List<UserDto> search(String keyword, Role role,Status status) {
		keyword = keyword == null ? "%%" : "%" + keyword + "%";
		List<User> users=userRepo.search(keyword, role,status);
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
		
		User duplicateEntry = userRepo.findByCode(user.getCode());
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
		UserUpdateEvent userUpdateEvent = new UserUpdateEvent(savedUser);
		eventPublisher.publishEvent(userUpdateEvent);
		return UserDto.of(savedUser);
	}

	@Override
	@Transactional
	public boolean deleteById(Long id) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser.getId() == id) {
			throw new ServiceException("Could not perform this operation.");
		}
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

	@Override
	@Transactional
	public void changePassword(Long userId, String password) {
		userRepo.updatePasswordById(password, userId);
	}

	@Override
	@Transactional
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		InvalidFieldsException invalidFieldsException = new InvalidFieldsException();
		
		User user = userRepo.findByIdAndPasswordAndIsDeleted(userId, oldPassword, false);
		if (user == null) {
			invalidFieldsException.addField(new InvalidField("oldPassword", "notFound", "The password is incorrect"));
		}
		
		if (invalidFieldsException.hasFields()) {
			throw invalidFieldsException;
		}
		
		user.setPassword(newPassword);
		userRepo.save(user);
	}

	@Override
	public Page<UserDto> search(UserSearch userSearch) {
		String keyword  = userSearch.getKeyword() == null ? "%%" : "%" + userSearch.getKeyword() + "%";
		Role role = userSearch.getRole();
		Status status = userSearch.getStatus();
		Pageable pageable = PageRequest.of(userSearch.getPage() - 1, userSearch.getSize(), userSearch.getSort().getSort());
		
		Page<User> userPage = userRepo.search(keyword, role,status, pageable);
		
		Page<UserDto> userDtoPage = new PageImpl<>(UserDto.ofList(userPage.getContent()), pageable, userPage.getTotalElements());
		
		return userDtoPage;
	}


}

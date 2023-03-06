package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.dto.UserSearch;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.entity.User.Status;

@Service
public interface UserService {
	List<UserDto> search(String keyword, Role role,Status status);
	
	Page<UserDto> search(UserSearch userSearch);

	Optional<UserDto> findById(Long id);

	UserDto save(User user);

	boolean deleteById(Long id);

	UserDto generateNewWithCode();

	void changePassword(Long userId, String passwrod);

	void changePassword(Long userId, String oldPassword, String newPassword);
}
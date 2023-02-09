package team.ojt7.recruitment.model.service;

import java.util.List;
import java.util.Optional;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;

public interface UserService {
    public List<UserDto> search(String keyword, Role role);
    public Optional<UserDto> findById(Long id);
    public UserDto save(User user);
    public boolean deleteById(Long id);

}
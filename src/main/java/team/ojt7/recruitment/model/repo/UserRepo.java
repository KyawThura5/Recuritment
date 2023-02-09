package team.ojt7.recruitment.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;

public interface UserRepo {
	
    public User create(User user);
    
    public User update(User user);
    
    public Optional<User> findById();

    public List<User> search(String keyword, Role role);

    public void deleteById(Long id);

}
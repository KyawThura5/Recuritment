package team.ojt7.recruitment.model.repo;

import java.util.*;

/**
 * 
 */
public interface UserRepo {

    /**
     * @param user 
     * @return
     */
    public User save(User user);

    /**
     * @return
     */
    public Optional<User> findById();

    /**
     * @param keyword 
     * @param role 
     * @return
     */
    public List<User> search(String keyword, Role role);

    /**
     * @param id 
     * @return
     */
    public boolean deleteById(Long id);

}
package team.ojt7.recruitment.model.service;

import java.util.*;

/**
 * 
 */
public interface UserService {

    /**
     * @param keyword 
     * @param role 
     * @return
     */
    public List<UserDto> search(String keyword, Role role);

    /**
     * @param id 
     * @return
     */
    public Optional<UserDto> findById(Long id);

    /**
     * @param user 
     * @return
     */
    public UserDto save(User user);

    /**
     * @param id 
     * @return
     */
    public boolean deleteById(Long id);

}
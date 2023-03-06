package team.ojt7.recruitment.model.repo;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.entity.User.Status;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
	@Query(value = "SELECT MAX(id) FROM user", nativeQuery = true)
    Long findMaxId();
	
	User findByCodeAndIsDeleted(String code, boolean isDeleted);
	
	User findByEmailAndIsDeleted(String email, boolean isDeleted);
	
	User findByPhoneAndIsDeleted(String phone, boolean isDeleted);
	
	User findByIdAndPasswordAndIsDeleted(Long id, String password, boolean isDeleted);
	
	Optional<User> findOneByCode(String code);
	
	Optional<User> findOneByStatus(String status);
	
	@Modifying
	@Query("UPDATE User SET password = :password WHERE id = :id")
	void updatePasswordById(
			@Param("password")
			String password,
			@Param("id")
			Long id);

    @Query("""
    		SELECT u FROM User u WHERE
    		(name LIKE :keyword OR code LIKE :keyword OR email LIKE :keyword OR phone LIKE :keyword)
    		AND (:role is null OR role = :role)
    		AND (:status is null OR status = :status)
    		AND is_deleted = false
    		""")
    public List<User> search(
    		@Param("keyword")
    		String keyword,
    		@Param("role")
    		Role role,
    		@Param("status")
    		Status status);

    @Modifying
    @Query(value = "UPDATE user SET is_deleted = true WHERE id = :id", nativeQuery = true)
    public void deleteById(@Param("id") Long id);
    
    @Query(value = """
    		SELECT u FROM User u WHERE
    		(name LIKE :keyword OR code LIKE :keyword OR email LIKE :keyword OR phone LIKE :keyword)
    		AND (:role is null OR role = :role)
    		AND (:status is null OR status = :status)
    		AND is_deleted = false
    		""",
    		countQuery = """
    		SELECT COUNT(u) FROM User u WHERE
    		(name LIKE :keyword OR code LIKE :keyword OR email LIKE :keyword OR phone LIKE :keyword)
    		AND (:role is null OR role = :role)
    		AND (:status is null OR status = :status)
    		AND is_deleted = false		
    				""")
    Page<User> search(
    		@Param("keyword")
    		String keyword,
    		@Param("role")
    		Role role,
    		@Param("status")
    		Status status,
    		Pageable pageable
    		);

}
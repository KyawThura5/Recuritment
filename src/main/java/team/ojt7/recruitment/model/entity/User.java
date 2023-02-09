package team.ojt7.recruitment.model.entity;

import java.util.*;

/**
 * 
 */
public class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private boolean isDeleted;

    /**
     * 
     */
    public enum Role {
        ADMIN,
        GENERAL_MANAGER,
        DEPARTMENT_HEAD,
        PROJECT_MANAGER,
        HIRING_MANAGER
    }

}
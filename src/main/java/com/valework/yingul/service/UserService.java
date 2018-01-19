package com.valework.yingul.service;

import java.util.List;
import java.util.Set;

import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.security.Yng_UserRole;

public interface UserService {
	Yng_User findByUsername(String username);

    Yng_User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
    
    void save (Yng_User user);
    
    Yng_User createUser(Yng_User user, Set<Yng_UserRole> userRoles);
    
    Yng_User saveUser (Yng_User user); 
    
    List<Yng_User> findUserList();

    void enableUser (String username);

    void disableUser (String username);
}

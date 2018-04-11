package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.security.Yng_Role;
import com.valework.yingul.model.security.Yng_UserRole;
import com.valework.yingul.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
	
	public void save(Yng_User user) {
        userDao.save(user);
    }

    public Yng_User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Yng_User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    
    public Yng_User createUser(Yng_User user, Set<Yng_UserRole> userRoles) {
        Yng_User localUser = userDao.findByEmail(user.getEmail());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (Yng_UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            user.setProfileBanner("sampleBanner.jpg");
            user.setProfilePhoto("profile.jpg");
            user.setProfileVideo("https://www.youtube.com/embed/1AV37mvCHQo");
            
            localUser = userDao.save(user);
        }

        return localUser;
    }
    
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public Yng_User saveUser (Yng_User user) {
        return userDao.save(user);
    }
    
    public List<Yng_User> findUserList() {
        return userDao.findAll();
    }

    public void enableUser (String username) {
        Yng_User user = findByUsername(username);
        user.setEnabled(true);
        userDao.save(user);
    }

    public void disableUser (String username) {
        Yng_User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userDao.save(user);
        System.out.println(username + " is disabled.");
    }

	public boolean isAdmin(Yng_User user) {
		Set<Yng_UserRole> listUserRole = user.getUserRoles();
		Yng_Role admin =roleDao.findByName("ADMIN"); 
		for (Yng_UserRole st : listUserRole) {
    		if(st.getRole()==admin) {
    			return true;
    		}
		}
		return false;
	}
}

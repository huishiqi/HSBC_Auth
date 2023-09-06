package com.auth.service.impl;

import com.auth.entity.Role;
import com.auth.entity.User;
import com.auth.service.UserService;
import com.auth.util.PasswordUtil;
import com.auth.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceImpl implements UserService {

    //to store the current all created users
    private final Map<String, User> allUsers = new ConcurrentHashMap<>();

    /**
     * Create user
     * @param username Username, not null or empty
     * @param password User password, not null or empty, will be encoded and stored
     * @return User created or not
     * @throws Exception when username or password is empty, or user already exists.
     */
    @Override
    public boolean createUser(String username, String password) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException("invalid username or password");
        }
        if (allUsers.containsKey(username)) {
            throw new RuntimeException("username already exist");
        }
        String encodedPassword = PasswordUtil.encode(password);
        User user = new User(username,encodedPassword);
        allUsers.put(username, user);
        return true;
    }

    /**
     * Delete user
     * @param username Username, not null or empty
     * @return User deleted or not
     * @throws Exception when username is null or empty or username not exist.
     */
    @Override
    public boolean deleteUser(String username) {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("invalid username");
        }
        if (!allUsers.containsKey(username)) {
            throw new RuntimeException("username not exist");
        }
        allUsers.remove(username);
        return true;
    }

    /**
     * Delete user
     * @param username Username, not null or empty
     * @param role Role to be added to user, not null
     * @return added successful or role is already exist
     * @throws Exception when username is null or empty, username not exist, or role is null
     */
    @Override
    public boolean addRole(String username, Role role) {
        if (StringUtils.isBlank(username) || role == null) {
            throw new RuntimeException("invalid username or role is null");
        }
        User user = allUsers.get(username);
        if (user == null) {
            throw new RuntimeException("user not exist");
        }
        Map<String, Role> userRoles = user.getUserRoles();
        userRoles.put(role.getRoleName(), role);

        return true;
    }

    /**
     * login check
     * @param username Username, not null or empty
     * @param password Password, not null or empty, encode and compare with the current password
     * @return successful or not
     * @throws Exception when username or password is null or empty
     */
    @Override
    public boolean checkUsernameAndPassword(String username, String password) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException("invalid username or password");
        }
        String encodedPassword = allUsers.get(username).getPassword();
        return PasswordUtil.passwordMatch(password, encodedPassword);
    }

    /**
     * user role check
     * @param username username, not null or empty
     * @param roleName roleName, not null or empty, roleName need to check
     * @return user contains or not, or roleName invalid
     */
    @Override
    public boolean checkRole(String username, String roleName) {
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(roleName)) {
            User user = allUsers.get(username);
            if (!Objects.isNull(user)) {
                Map<String, Role> userRoles = user.getUserRoles();
                return userRoles.containsKey(roleName);
            }
        }
        return false;
    }

    /**
     * get user all roles
     * @param username username, not null or empty
     * @return all user role or null for invalid token
     */
    @Override
    public List<Role> getUserRoles(String username) {
        if (!StringUtils.isBlank(username)) {
            User user = allUsers.get(username);
            if (!Objects.isNull(user)) {
                Map<String, Role> userRoles = user.getUserRoles();
                return new ArrayList<>(userRoles.values());
            }
        }
        return new ArrayList<>();
    }
}

package com.auth.service;

import com.auth.entity.Role;

import java.util.List;

public interface UserService {
    boolean checkUsernameAndPassword(String username, String password) throws Exception;

    boolean createUser(String username, String password) throws Exception;

    boolean deleteUser(String username);

    boolean addRole(String username, Role role);

    boolean checkRole(String username, String roleName);

    List<Role> getUserRoles(String username);
}

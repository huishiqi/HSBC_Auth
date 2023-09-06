package com.auth.api;

import com.auth.entity.Role;

import java.util.List;

public interface AuthApi {
    boolean createUser(String username, String password) throws Exception;

    boolean deleteUser(String username);

    boolean createRole(String roleName);

    boolean deleteRole(String roleName);

    boolean addRoleToUser(String username, String roleName);

    String login(String username, String password) throws Exception;

    void logout(String token);

    boolean hasRole(String token, String roleName);

    List<Role> myAllRoles(String token);

}

package com.auth.api.impl;

import com.auth.api.AuthApi;
import com.auth.entity.Role;
import com.auth.service.AuthService;
import com.auth.service.RoleService;
import com.auth.service.UserService;
import com.auth.util.StringUtils;

import java.util.List;

public class AuthApiImpl implements AuthApi {

    private RoleService roleService;
    private UserService userService;
    private AuthService authService;

    public AuthApiImpl(RoleService roleService, UserService userService, AuthService authService) {
        this.roleService = roleService;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public boolean createUser(String username, String password) throws Exception {
        return userService.createUser(username, password);
    }

    @Override
    public boolean deleteUser(String username) {
        return userService.deleteUser(username);
    }

    @Override
    public boolean createRole(String roleName) {
        return roleService.createRole(roleName);
    }

    @Override
    public boolean deleteRole(String roleName) {
        return roleService.deleteRole(roleName);
    }

    @Override
    public boolean addRoleToUser(String username, String roleName) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(roleName)) {
            throw new RuntimeException("invalid username or roleName");
        }
        Role role = roleService.getRoleByRoleName(roleName);
        return userService.addRole(username, role);
    }

    @Override
    public String login(String username, String password) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException("invalid username or password");
        }
        if (!userService.checkUsernameAndPassword(username, password)) {
            throw new RuntimeException("username and password check failed");
        }
        return authService.getToken(username);
    }

    @Override
    public void logout(String token) {
        authService.invalidateToken(token);
    }

    @Override
    public boolean hasRole(String token, String roleName) {
        if (StringUtils.isBlank(token) || StringUtils.isBlank(roleName)) {
            throw new RuntimeException("invalid token or roleName");
        }
        String username = authService.checkTokenAndGetUsername(token);
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("token is invalid or expired");
        }
        return userService.checkRole(username, roleName);
    }

    @Override
    public List<Role> myAllRoles(String token) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("invalid token");
        }
        String username = authService.checkTokenAndGetUsername(token);
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("token is invalid or expired");
        }
        return userService.getUserRoles(username);
    }
}

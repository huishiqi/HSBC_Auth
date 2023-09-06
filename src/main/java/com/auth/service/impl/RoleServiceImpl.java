package com.auth.service.impl;

import com.auth.entity.Role;
import com.auth.service.RoleService;
import com.auth.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleServiceImpl implements RoleService {

    private final Map<String, Role> allRoles = new ConcurrentHashMap<>();

    /**
     * create a new role
     * @param roleName roleName, not null or empty
     * @return role created or not or role is already exist
     * @throws Exception when roleName is null or empty, role already exist
     */
    @Override
    public boolean createRole(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            throw new RuntimeException("roleName is invalid");
        }
        if (allRoles.containsKey(roleName)) {
            throw new RuntimeException("role already exist");
        }
        Role role = new Role(roleName);
        allRoles.put(roleName, role);
        return true;
    }

    /**
     * delete a role
     * @param roleName roleName, not null or empty
     * @return role deleted or not or role is not exist
     * @throws Exception when roleName is null or empty, role not exist
     */
    @Override
    public boolean deleteRole(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            throw new RuntimeException("roleName is invalid");
        }
        if (!allRoles.containsKey(roleName)) {
            throw new RuntimeException("role not exist");
        }
        allRoles.remove(roleName);
        return true;
    }

    /**
     * get role by roleName
     * @param roleName roleName, not null or empty
     * @return role
     * @throws Exception when roleName is null or empty
     */
    @Override
    public Role getRoleByRoleName(String roleName){
        if (StringUtils.isBlank(roleName)) {
            throw new RuntimeException("roleName is invalid");
        }
        return allRoles.get(roleName);
    }
}

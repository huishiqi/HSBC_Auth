package com.auth.service;

import com.auth.entity.Role;

public interface RoleService {
    boolean createRole(String roleName);

    boolean deleteRole(String roleName);

    Role getRoleByRoleName(String roleName);
}

package com.auth.service;

import com.auth.service.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoleServiceTest {
    private RoleService roleService;

    @Before
    public void init() {
        roleService = new RoleServiceImpl();
        roleService.createRole("role1");
    }

    @Test
    public void createRoleTest() {
        Assert.assertTrue(roleService.createRole("role2"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> roleService.createRole("role1"));
        Assert.assertEquals("role already exist", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> roleService.createRole(""));
        Assert.assertEquals("roleName is invalid", e2.getMessage());

        Exception e3 = Assert.assertThrows(RuntimeException.class, () -> roleService.createRole(null));
        Assert.assertEquals("roleName is invalid", e3.getMessage());
    }

    @Test
    public void deleteRoleTest() {
        Assert.assertTrue(roleService.deleteRole("role1"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> roleService.deleteRole("role2"));
        Assert.assertEquals("role not exist", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> roleService.deleteRole(""));
        Assert.assertEquals("roleName is invalid", e2.getMessage());

        Exception e3 = Assert.assertThrows(RuntimeException.class, () -> roleService.deleteRole(null));
        Assert.assertEquals("roleName is invalid", e3.getMessage());
    }

    @Test
    public void getRoleByRoleNameTest() {
        Assert.assertNotNull(roleService.getRoleByRoleName("role1"));
        Assert.assertNull(roleService.getRoleByRoleName("role2"));

        Exception e = Assert.assertThrows(RuntimeException.class, () -> roleService.getRoleByRoleName(""));
        Assert.assertEquals("roleName is invalid", e.getMessage());

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> roleService.getRoleByRoleName(null));
        Assert.assertEquals("roleName is invalid", e1.getMessage());
    }
}

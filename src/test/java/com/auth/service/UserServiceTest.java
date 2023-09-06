package com.auth.service;

import com.auth.entity.Role;
import com.auth.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {
    private UserService userService;
    private final Role role = new Role("role1");

    @Before
    public void init() throws Exception {
        userService = new UserServiceImpl();
        userService.createUser("Sissi", "password");
        userService.addRole("Sissi", role);
    }

    @Test
    public void createUserTest() throws Exception {
        Assert.assertTrue(userService.createUser("Sandy", "pwd"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> userService.createUser("Sissi", "password"));
        Assert.assertEquals("username already exist", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> userService.createUser("", ""));
        Assert.assertEquals("invalid username or password", e2.getMessage());

        Exception e3 = Assert.assertThrows(RuntimeException.class, () -> userService.createUser("Sissi", null));
        Assert.assertEquals("invalid username or password", e3.getMessage());
    }

    @Test
    public void deleteUserTest() {
        Assert.assertTrue(userService.deleteUser("Sissi"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> userService.deleteUser("Sandy"));
        Assert.assertEquals("username not exist", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> userService.deleteUser(""));
        Assert.assertEquals("invalid username", e2.getMessage());

        Exception e3 = Assert.assertThrows(RuntimeException.class, () -> userService.deleteUser(null));
        Assert.assertEquals("invalid username", e3.getMessage());
    }

    @Test
    public void addRoleTest() {
        Assert.assertTrue(userService.addRole("Sissi", role));
        Assert.assertTrue(userService.addRole("Sissi", new Role("role2")));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> userService.addRole("", new Role("role1")));
        Assert.assertEquals("invalid username or role is null", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> userService.addRole("Sandy", new Role("role1")));
        Assert.assertEquals("user not exist", e2.getMessage());
    }

    @Test
    public void checkUserTest() throws Exception {
        Assert.assertTrue(userService.checkUsernameAndPassword("Sissi", "password"));
        Assert.assertFalse(userService.checkUsernameAndPassword("Sissi", "pwd"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> userService.checkUsernameAndPassword("", "pwd"));
        Assert.assertEquals("invalid username or password", e1.getMessage());
    }

    @Test
    public void checkRoleTest() {
        Assert.assertTrue(userService.checkRole("Sissi", role.getRoleName()));
        Assert.assertFalse(userService.checkRole("Sissi", ""));
        Assert.assertFalse(userService.checkRole("Sissi", "role2"));
    }

    @Test
    public void myAllRolesTest() {
        Assert.assertEquals(1, userService.getUserRoles("Sissi").size());
        Assert.assertEquals(0, userService.getUserRoles("Sandy").size());
        Assert.assertEquals(0, userService.getUserRoles("").size());
    }
}

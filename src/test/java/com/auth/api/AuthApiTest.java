package com.auth.api;

import com.auth.api.impl.AuthApiImpl;
import com.auth.entity.Role;
import com.auth.service.AuthService;
import com.auth.service.RoleService;
import com.auth.service.UserService;
import com.auth.service.impl.AuthServiceImpl;
import com.auth.service.impl.RoleServiceImpl;
import com.auth.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AuthApiTest {
    private AuthApi authApi;
    private final AuthService authService = Mockito.mock(AuthServiceImpl.class);
    private final UserService userService = Mockito.mock(UserServiceImpl.class);
    private final RoleService roleService = Mockito.mock(RoleServiceImpl.class);

    @Before
    public void init() {
        authApi = new AuthApiImpl(roleService, userService, authService);
    }

    @Test
    public void createUserTest() throws Exception {
        Mockito.when(userService.createUser("Sissi", "password1")).thenReturn(true);
        Mockito.when(userService.createUser("", "pwd")).thenReturn(false);
        Mockito.when(userService.createUser("Sandy", "")).thenReturn(false);
        Assert.assertTrue(authApi.createUser("Sissi", "password1"));
        Assert.assertFalse(authApi.createUser("", "pwd"));
        Assert.assertFalse(authApi.createUser("Sandy", ""));
    }

    @Test
    public void deleteUserTest() {
        Mockito.when(userService.deleteUser("Sissi")).thenReturn(true);
        Mockito.when(userService.deleteUser("qas")).thenReturn(false);
        Mockito.when(userService.deleteUser("")).thenReturn(false);
        Mockito.when(userService.deleteUser(null)).thenReturn(false);
        Assert.assertTrue(authApi.deleteUser("Sissi"));
        Assert.assertFalse(authApi.deleteUser("qas"));
        Assert.assertFalse(authApi.deleteUser(""));
        Assert.assertFalse(authApi.deleteUser(null));
    }

    @Test
    public void createRoleTest() {
        Mockito.when(roleService.createRole("role3")).thenReturn(true);
        Mockito.when(roleService.createRole("")).thenReturn(false);
        Mockito.when(roleService.createRole("role1")).thenReturn(false);
        Mockito.when(roleService.createRole(null)).thenReturn(false);
        Assert.assertTrue(authApi.createRole("role3"));
        Assert.assertFalse(authApi.createRole(""));
        Assert.assertFalse(authApi.createRole("role1"));
        Assert.assertFalse(authApi.createRole(null));
    }

    @Test
    public void deleteRoleTest() {
        Mockito.when(roleService.deleteRole("role1")).thenReturn(true);
        Mockito.when(roleService.deleteRole("")).thenReturn(false);
        Mockito.when(roleService.deleteRole("role5")).thenReturn(true);
        Mockito.when(roleService.deleteRole(null)).thenReturn(false);
        Assert.assertTrue(authApi.deleteRole("role1"));
        Assert.assertFalse(authApi.deleteRole(""));
        Assert.assertTrue(authApi.deleteRole("role5"));
        Assert.assertFalse(authApi.deleteRole(null));
    }

    @Test
    public void addRoleToUserTest() {
        Role role = new Role("role1");
        Mockito.when(roleService.getRoleByRoleName("role1")).thenReturn(role);
        Mockito.when(userService.addRole("Sissi", role)).thenReturn(true);
        Assert.assertTrue(authApi.addRoleToUser("Sissi", "role1"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authApi.addRoleToUser("", "role1"));
        Assert.assertEquals("invalid username or roleName", e1.getMessage());

        Mockito.when(userService.addRole("Sandy", role)).thenThrow(new RuntimeException("user not exist"));
        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> authApi.addRoleToUser("Sandy", "role1"));
        Assert.assertEquals("user not exist", e2.getMessage());
    }

    @Test
    public void loginTest() throws Exception {
        Mockito.when(userService.checkUsernameAndPassword("Sissi", "password1")).thenReturn(true);
        Mockito.when(authService.getToken("Sissi")).thenReturn("asd123");
        Assert.assertEquals("asd123", authApi.login("Sissi", "password1"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authApi.login("", "role1"));
        Assert.assertEquals("invalid username or password", e1.getMessage());

        Mockito.when(userService.checkUsernameAndPassword("Sissi", "pwd")).thenReturn(false);
        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> authApi.login("Sissi", "pwd"));
        Assert.assertEquals("username and password check failed", e2.getMessage());
    }

    @Test
    public void hasRoleTest() {
        Mockito.when(authService.checkTokenAndGetUsername("asd123")).thenReturn("Sissi");
        Mockito.when(userService.checkRole("Sissi", "role1")).thenReturn(true);
        Mockito.when(userService.checkRole("Sissi", "role2")).thenReturn(false);
        Assert.assertTrue(authApi.hasRole("asd123", "role1"));
        Assert.assertFalse(authApi.hasRole("asd123", "role2"));

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authApi.hasRole("", "role1"));
        Assert.assertEquals("invalid token or roleName", e1.getMessage());

        Mockito.when(authService.checkTokenAndGetUsername("qwe")).thenReturn("");
        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> authApi.hasRole("qwe", "role1"));
        Assert.assertEquals("token is invalid or expired", e2.getMessage());
    }

    @Test
    public void myAllRolesTest() throws Exception {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role("role1"));

        Mockito.when(authService.checkTokenAndGetUsername("asd123")).thenReturn("Sissi");
        Mockito.when(userService.getUserRoles("Sissi")).thenReturn(roleList);
        Assert.assertEquals(1, authApi.myAllRoles("asd123").size());

        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authApi.myAllRoles(""));
        Assert.assertEquals("invalid token", e1.getMessage());

        Mockito.when(authService.checkTokenAndGetUsername("token")).thenReturn("");
        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> authApi.myAllRoles("token"));
        Assert.assertEquals("token is invalid or expired", e2.getMessage());
    }
}

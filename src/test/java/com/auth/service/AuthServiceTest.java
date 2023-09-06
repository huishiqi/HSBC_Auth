package com.auth.service;

import com.auth.service.impl.AuthServiceImpl;
import com.auth.util.JwtUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class AuthServiceTest {
    private AuthService authService;
    private static final long EXPIRE_TIME = 120*60*1000;

    @Before
    public void init() {
        authService = new AuthServiceImpl();
    }

    @Test
    public void getTokenTest() throws Exception {
        Assert.assertNotNull(authService.getToken("Sissi"));
        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authService.getToken(""));
        Assert.assertEquals("invalid username", e1.getMessage());
    }

    @Test
    public void invalidTokenTest() {
        String token = JwtUtils.generateToken("Sissi", new Date(System.currentTimeMillis() + EXPIRE_TIME));
        authService.invalidateToken(token);
        Exception e1 = Assert.assertThrows(RuntimeException.class, () -> authService.checkTokenAndGetUsername("token"));
        Assert.assertEquals("token is empty or is invalid", e1.getMessage());

        Exception e2 = Assert.assertThrows(RuntimeException.class, () -> authService.invalidateToken(""));
        Assert.assertEquals("invalid token provided", e2.getMessage());
    }

    @Test
    public void checkToken() throws Exception {
        String token = authService.getToken("Sissi");
        String username = authService.checkTokenAndGetUsername(token);
        Assert.assertEquals("Sissi", username);
    }

}

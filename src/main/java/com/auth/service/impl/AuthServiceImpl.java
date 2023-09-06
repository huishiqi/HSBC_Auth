package com.auth.service.impl;

import com.auth.service.AuthService;
import com.auth.util.JwtUtils;
import com.auth.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AuthServiceImpl implements AuthService {

    //use this map to store the current valid token, once user logout, the token will be removed.
    private Map<String,String> validToken = new ConcurrentHashMap<>();

    //set token expire time
    private static final long EXPIRE_TIME = 120*60*1000;
    /**
     * login
     * @param username Username, not null or empty
     * @return token for login successful, or null for login failed
     */
    @Override
    public String getToken(String username) {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("invalid username");
        }
        //set token expire time
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = JwtUtils.generateToken(username, expireDate);
        validToken.put(token, "valid");
        return token;
    }

    /**
     * logout
     * @param authToken authToken, not null or empty
     */
    @Override
    public void invalidateToken(String authToken) {
        if (StringUtils.isBlank(authToken)) {
            throw new RuntimeException("invalid token provided");
        }
        validToken.remove(authToken);
    }

    @Override
    public String checkTokenAndGetUsername(String token) {
        if (StringUtils.isBlank(token) || !validToken.containsKey(token)) {
            throw new RuntimeException("token is empty or is invalid");
        }
        return JwtUtils.getUserNameByToken(token);
    }
}





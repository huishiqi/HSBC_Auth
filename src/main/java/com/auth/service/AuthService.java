package com.auth.service;

public interface AuthService {

    String getToken(String username) throws Exception;

    void invalidateToken(String authToken);

    String checkTokenAndGetUsername(String token);
}

package com.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final String TOKEN_SECRET = "123456789abcdefg";

    //generate token by JWT with username claimed
    public static String generateToken(String username, Date expireDate) {
        //set header
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");
        header.put("alg", "HS256");

        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        //generate token with user info
        String token = JWT.create()
                .withHeader(header)
                .withClaim("username", username)
                .withExpiresAt(expireDate)
                .sign(algorithm);
        return token;
    }

    public static String getUserNameByToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("token is invalid");
        }
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        String username = "";

        try {
            DecodedJWT jwt;
            jwt = verifier.verify(token);
            username = jwt.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token is invalid or expired");
        }

        return username;

    }

}

package com.zhugs.common.core.util;

import com.zhugs.common.core.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET.getBytes());

    public static String createToken(String subject, Map<String, ?> claims) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + JwtConstant.JWT_EXPIRATION_THREE_SECOND))
                .subject(subject)
                .claims(claims)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public static Map<String, ?> parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return claimsJws.getPayload();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }

    public static boolean isJwtTooLarge(String jwt) {
        int size = jwt.getBytes(StandardCharsets.UTF_8).length;
        return size > JwtConstant.JWT_BYTE_SIZE_MAX;
    }


}

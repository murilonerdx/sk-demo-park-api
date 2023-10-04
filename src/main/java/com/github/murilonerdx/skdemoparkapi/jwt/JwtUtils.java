package com.github.murilonerdx.skdemoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Data
public class JwtUtils {
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "1234567890-1234567890-1234567890-1234567890";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRER_MINUTES = 2;

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRER_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(String username, String role) {
        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);

        return new JwtToken(
                Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .setSubject(username)
                        .setIssuedAt(issueAt)
                        .setExpiration(limit)
                        .signWith(generateKey(), SignatureAlgorithm.HS256)
                        .claim("role", role)
                        .compact()
        );
    }

    private static Claims getClaimsFromToken(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(generateKey())
                    .build()
                    .parseClaimsJwt(refactorToken(token)).getBody();
        }catch(JwtException ex){
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return null;
    }

    public static String getUsername(String token){
        return Objects.requireNonNull(getClaimsFromToken(token)).getSubject();
    }

    public static boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(generateKey())
                    .build()
                    .parseClaimsJwt(refactorToken(token)).getBody();
            return true;
        }catch(JwtException ex){
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }

        return false;
    }

    private static String refactorToken(String token) {
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}

package com.example.lr_3.component;


import com.example.lr_3.token.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.UUID;


@Component
public class JwtUserIdTokenUtil implements Serializable, JwtTokenUtil<UUID> {
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    public boolean validateToken(String token, UUID id) {
        final UUID uuid = getSubjectFromToken(token);
        return (uuid.equals(id) && !isTokenExpired(token));
    }
    public UUID getSubjectFromToken(String token){
        String id = getClaimFromToken(token, Claims::getSubject);
        id = id.substring(0, id.indexOf(secret));
        return UUID.fromString(id);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(secret.getBytes())
                .build();
        return (Claims) jwtParser.parse(token).getBody();
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(UUID id) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, id+secret);
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .compact();
    }
}

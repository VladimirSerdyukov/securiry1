package ru.security.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final long EXPIRATION_TIME = 24*60*60*1000;//24 часа
    @Value("${jwt.token.secret}")
    private String key;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());
        JwtBuilder builder = Jwts.builder();
        builder.claims().add(claims);
        builder.subject(userDetails.getUsername());
        builder.issuedAt(new Date(System.currentTimeMillis()));
        builder.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        builder.signWith(getSignInKey());
        return builder.compact();
    }

    private SecretKey getSignInKey () {
        byte [] keyBytes = key.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        System.out.println("ExtractMethod: " + extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        System.out.println("Method extractAllClaims  " + claims.toString());
        return claims;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

package com.example.hdrezka.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String secret = "e7cafe86e9b5da87e6b49b0fd83b71eb37233616a6738dc5399287b80cc24109b87c2c88c4687f68f679f6105efbe6ddec565e5c1c72b027af8e40adcde45f6eb9912d51f573b85cffbac1b403b6dd18484f66345cb9cf9e442b7dc98f78511ee76433cf22e83d8cae9e3cbf45cb5b6b528785f3755546c7d832ba89311bdaa80df6d250072a03ac72806c4395d1ed3b1670e242d57301c04f8a2d87ebe74b69b924394399c1af9f2b0e54eb178ec615ed058c75f0863a7e57c1ad721136f3b4ea8e4a1a9d9d1117daa9b92cc9fb85e189eacb8324ad2c618134d2422e4adf24b68b48a5a9fc11e85f16222a15d36ca500a6a6bd7106fe5140c8820c1b11dbdb6fec7380224351c91144a4a21987e0fa6ef741196ab7152246df9b857b9ea202";

    public JwtUtil() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}

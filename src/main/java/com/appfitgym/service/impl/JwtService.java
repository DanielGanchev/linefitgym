package com.appfitgym.service.impl;


import com.appfitgym.model.entities.LineFitGymUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${app.token.secretKey}")
    private String jwtSigningKey;
    @Value("${spring.application.name}")
    private String appName;
    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String userName = extractUserName(token);

        return (userName.equals(userDetails.getUsername())) && isTokenNonExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public String generateToken( LineFitGymUserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (isTokenNonExpired(token) || Boolean.TRUE.equals(ignoreTokenExpiration(token)));
    }

    private String generateToken(Map<String, Object> extraClaims, LineFitGymUserDetails userDetails) {
        extraClaims.put("authorities",
                userDetails
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
        extraClaims.put("fullName",
                userDetails.getFullName());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setId(String.valueOf(userDetails.getId()))
                .setSubject(userDetails.getUsername())
                .setIssuer(appName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    private Boolean ignoreTokenExpiration(String token) {
        return null;
    }


    private boolean isTokenNonExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

}
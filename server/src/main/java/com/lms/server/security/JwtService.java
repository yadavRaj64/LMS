package com.lms.server.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import com.lms.server.model.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {

    @Value("${lms.app.jwtSecret}")
    private String jwtSecret;

    @Value("${lms.app.jwtExpiration}")
    private long jwtExpiration;

    public boolean isTokenValid(String token, UserPrincipal userDetails) {
        final Long userID = extractUserID(token);
        return (userID == userDetails.getUserID()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserPrincipal userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserPrincipal userDetails,
            long expiration) {
        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUserID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .and()
                .signWith(getSignInKey())
                .compact();

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserID(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

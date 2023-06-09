package com.example.servercw.services;

import com.example.servercw.models.accountmodel.AccountModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * service for generating and validating tokens
 */
@Service
public class JwtService {
    private static final String SECRET_KEY = "4D635166546A576D5A7134743777217A25432A462D4A614E645267556B587032";
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * @param extraClaims
     * @param userDetails
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @param jwtToken
     * @param userDetails
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userEmail = extractUserLogin(jwtToken);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    /**
     * @param jwtToken
     */
    public String extractUserLogin(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * @param jwtToken
     * @param claimsResolver
     * @param <T>
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * @param jwtToken
     */
    private boolean isTokenExpired(String jwtToken){
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * @param jwtToken
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * @param jwtToken
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * @return
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

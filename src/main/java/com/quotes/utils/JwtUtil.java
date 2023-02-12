package com.quotes.utils;

import com.quotes.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationdays}")
    private Long expirationDays;

    public JwtTokenDto generateToken(String email,
                                     Collection<? extends GrantedAuthority> authorities,
                                     boolean enabled,
                                     boolean accountNonLocked) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email);
        claims.put("enabled", enabled);
        claims.put("authorities", authorities);
        claims.put("accountNonLocked", accountNonLocked);
        String token = createToken(claims);

        return new JwtTokenDto(
                extractUsername(token),
                extractAuthorities(token).get(0),
                extractExpiration(token),
                token
        );
    }

    private String createToken(Map<String, Object> claims) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(expirationDays, ChronoUnit.DAYS);

        log.info("Issued at: {}", issuedAt);
        log.info("Expires at: {}", expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractAuthorities(String token) {
        final Claims claims = extractAllClaims(token);
        List<LinkedHashMap<String, String>> authorities = claims.get("authorities", List.class);
        return authorities.stream()
                .map(v -> v.get("authority"))
                .collect(Collectors.toList());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
    }

    public Boolean validateToken(String token, String email) {
        final String username = extractUsername(token);
        return (username.equals(email) && !isTokenExpired(token));
    }
}

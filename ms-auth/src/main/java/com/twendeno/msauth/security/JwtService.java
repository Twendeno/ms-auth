package com.twendeno.msauth.security;

import com.twendeno.msauth.user.User;
import com.twendeno.msauth.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
@AllArgsConstructor
public class JwtService {

    private final String ENCRYPTION_KEY ="c767055c8577301380ee11a870ef6b302c658104e3bac9eece7db8bd4503b486";
    private final UserService userService;
    public Map<String,String> generateToken(String username) {
        User user = this.userService.loadUserByUsername(username);
        return this.generateJwt(user);
    }

    private Map<String,String> generateJwt(User user) {

        final long currentTimeMillis = System.currentTimeMillis();
        final long expirationTime = currentTimeMillis + 30 * 60 * 1000; // 30 minutes

        Map<String, Object> claims = Map.of(
                "name", user.getUsername(),
                "email", user.getEmail(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT, user.getUsername()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(this.getKey(), HS256)
                .compact();

        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decode = Decoders.BASE64.decode(this.ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T>  T getClaim(String token, Function<Claims,T> function) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return function.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

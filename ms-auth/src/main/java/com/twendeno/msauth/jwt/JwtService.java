package com.twendeno.msauth.jwt;

import com.twendeno.msauth.refreshToken.RefreshToken;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.auth.AuthService;
import com.twendeno.msauth.auth.dto.RefreshTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class JwtService {

    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "c767055c8577301380ee11a870ef6b302c658104e3bac9eece7db8bd4503b486";

    private final AuthService authService;
    private final JwtRepository jwtRepository;


    public Jwt tokenByValue(String token) {
        return this.jwtRepository.findByValueAndDisableAndExpired(token, false, false).orElseThrow(() -> new SignatureException("Token not found"));
    }

    private void disableTokens(User user) {
        List<Jwt> jwtList = this.jwtRepository.findUser(user.getEmail()).peek(jwt -> {
            jwt.setDisable(true);
            jwt.setExpired(true);
        }).toList();

        this.jwtRepository.saveAll(jwtList);

    }

    public Map<String, String> generateToken(String username) {
        User user = this.authService.loadUserByUsername(username);
        this.disableTokens(user);
        Map<String, String> jwtMap = new HashMap<String, String>(this.generateJwt(user));

        RefreshToken refreshToken = RefreshToken.builder()
                .expired(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(5 * 60 * 1000)) // 5 minutes
                .value(UUID.randomUUID().toString())
                .build();

        Jwt jwt = Jwt
                .builder()
                .value(jwtMap.get(BEARER))
                .disable(false)
                .expired(false)
                .user(user)
                .refreshToken(refreshToken)
                .build();

        this.jwtRepository.save(jwt);

        jwtMap.put("refreshToken", refreshToken.getValue());

        return jwtMap;
    }

    private Map<String, String> generateJwt(User user) {

        final long currentTimeMillis = System.currentTimeMillis();
        final long expirationTime = currentTimeMillis + 30 * 60 * 1000; // 30 minutes

        Map<String, Object> claims = Map.of(
                "name", user.getUsername(),
                "email", user.getEmail(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getUsername()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(this.getKey(), HS256)
                .compact();

        return Map.of(BEARER, bearer);
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

    private <T> T getClaim(String token, Function<Claims, T> function) {
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

    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUserValidToken(user.getEmail(), false, false).orElseThrow(() -> new JwtException("Token invalid"));

        jwt.setExpired(true);
        jwt.setDisable(true);

        this.jwtRepository.save(jwt);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void removeUselessJwt() {
        this.jwtRepository.deleteAllByExpiredAndDisable(true, true);
    }

    public Map<String, String> refreshToken(RefreshTokenDto refreshTokenDto) {
        Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenDto.refreshToken()).orElseThrow(() -> new JwtException("Token invalid"));

        if (jwt.getRefreshToken().isExpired() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new JwtException("Token expired");
        }

        this.disableTokens(jwt.getUser());
        return this.generateToken(jwt.getUser().getEmail());
    }
}

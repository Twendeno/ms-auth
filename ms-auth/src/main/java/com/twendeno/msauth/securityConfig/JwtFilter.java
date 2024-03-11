package com.twendeno.msauth.securityConfig;

import com.twendeno.msauth.jwt.Jwt;
import com.twendeno.msauth.jwt.JwtService;
import com.twendeno.msauth.auth.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver handlerExceptionResolver;
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        boolean isTokenExpired = true;

        Jwt tokenOnDb = null;

        try {

            String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);

                tokenOnDb = this.jwtService.tokenByValue(token);

                isTokenExpired = this.jwtService.isTokenExpired(token);

                username = this.jwtService.extractUsername(token);
            }

            if (
                    !isTokenExpired
                            && tokenOnDb.getUser().getEmail().equals(username)
                            && SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                UserDetails userDetails = authService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        }catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}

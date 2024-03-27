package com.twendeno.msauth.auth;

import com.twendeno.msauth.auth.dto.*;
import com.twendeno.msauth.jwt.JwtService;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.validation.dto.ValidationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Map<String, String>>> authenticateUser(@Valid @RequestBody SignInDto signInDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.username(), signInDto.password())
        );

        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                    .status(HttpStatus.OK.getReasonPhrase().toLowerCase())
                    .code(HttpStatus.OK.value())
                    .message("User authenticated")
                    .data(jwtService.generateToken(signInDto.username()))
                    .build());
        }

        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase().toLowerCase())
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("User not authenticated")
                .data(null)
                .build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        return new ResponseEntity<>(this.authService.signUp(signUpDto), HttpStatus.CREATED);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/activation")
    public ResponseEntity<ApiResponse<String>> activation(@Valid @RequestBody ValidationDto validationDto) {
        return ResponseEntity.ok(this.authService.activation(validationDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/sign-out")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(this.jwtService.logout());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/password-reset")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(this.authService.resetPassword(resetPasswordDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/new-password")
    public ResponseEntity<ApiResponse<String>> newPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        return ResponseEntity.ok(this.authService.newPassword(newPasswordDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/refresh-token")
    public ResponseEntity<ApiResponse<Map<String, String>>> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .status(HttpStatus.OK.getReasonPhrase().toLowerCase())
                .code(HttpStatus.OK.value())
                .message("Token refreshed")
                .data(jwtService.refreshToken(refreshTokenDto))
                .build());
    }
}

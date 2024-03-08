package com.twendeno.msauth.user;

import com.twendeno.msauth.jwt.JwtService;
import com.twendeno.msauth.user.dto.*;
import com.twendeno.msauth.validation.dto.ValidationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    public Map<String, String> authenticateUser(@RequestBody SignInDto signInDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.username(), signInDto.password())
        );

        if (authenticate.isAuthenticated()) {
            return this.jwtService.generateToken(signInDto.username());
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void registerUser(@RequestBody SignUpDto signUpDto) {
        this.userService.signUp(signUpDto);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/activation")
    public void activation(@RequestBody ValidationDto validationDto) {
        this.userService.activation(validationDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/signout")
    public void logout() {
        this.jwtService.logout();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/password-reset")
    public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        this.userService.resetPassword(resetPasswordDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/new-password")
    public void newPassword(@RequestBody NewPasswordDto newPasswordDto) {
        this.userService.newPassword(newPasswordDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/refresh-token")
    public @ResponseBody Map<String,String> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return this.jwtService.refreshToken(refreshTokenDto);
    }

}

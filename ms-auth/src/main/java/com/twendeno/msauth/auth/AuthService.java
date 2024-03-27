package com.twendeno.msauth.auth;

import com.twendeno.msauth.advice.EmailAlreadyExistsException;
import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.auth.dto.NewPasswordDto;
import com.twendeno.msauth.auth.dto.ResetPasswordDto;
import com.twendeno.msauth.auth.dto.SignUpDto;
import com.twendeno.msauth.role.Role;
import com.twendeno.msauth.role.RoleRepository;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.validation.Validation;
import com.twendeno.msauth.validation.ValidationService;
import com.twendeno.msauth.validation.dto.ValidationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public ApiResponse<String> signUp(SignUpDto signUpDto) {
        Optional<User> userFound = userRepository.findByEmail(signUpDto.email());

        if (userFound.isPresent()) {
            throw new EmailAlreadyExistsException("Email is already taken!");
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.name());
        user.setEmail(signUpDto.email());
        user.setPassword(passwordEncoder.encode(signUpDto.password()));
        user.setRole(signUpDto.role());

        Role role = roleRepository.findByName(signUpDto.role().getName()).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.setRole(role);

        switch (signUpDto.role().getName()){
            case USER, DEV -> user.setEnable(false);
            default -> user.setEnable(true);
        }

        user = userRepository.save(user);

        switch (signUpDto.role().getName()){
            case USER, DEV -> this.validationService.saveValidation(user);
        }

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
                .code(HttpStatus.OK.value())
                .message("User registered successfully")
                .data(user.getEmail())
                .build();
    }

    public ApiResponse<String> activation(ValidationDto validationDto) {
        Validation validation = this.validationService.getValidation(validationDto.code());

        if (validation.getExpiration().isBefore(validation.getCreation())) {
            throw new RuntimeException("Validation code has expired");
        }

        User user = this.userRepository.findById(validation.getUser().getUuid()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getUsername().equals(validationDto.username())) {
            throw new RuntimeException("You are not authorized to activate this account");
        }

        user.setEnable(true);
        this.userRepository.save(user);

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
                .code(HttpStatus.OK.value())
                .message("User activated successfully")
                .data(user.getEmail())
                .build();
    }

    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with" + username));
    }

    public ApiResponse<String> resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = this.loadUserByUsername(resetPasswordDto.email());

        this.validationService.saveValidation(user);
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
                .code(HttpStatus.OK.value())
                .message("Reset password link sent to your email")
                .data(user.getEmail())
                .build();

    }

    public ApiResponse<String> newPassword(NewPasswordDto newPasswordDto) {
        User user = this.loadUserByUsername(newPasswordDto.email());
        Validation validation = this.validationService.getValidation(newPasswordDto.code());

        if (validation.getUser().getEmail().equals(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.password()));
            this.userRepository.save(user);
        }

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
                .code(HttpStatus.OK.value())
                .message("Password reset successfully")
                .data(user.getEmail())
                .build();
    }
}

package com.twendeno.msauth.auth;

import com.twendeno.msauth.auth.dto.NewPasswordDto;
import com.twendeno.msauth.auth.dto.ResetPasswordDto;
import com.twendeno.msauth.auth.dto.SignUpDto;
import com.twendeno.msauth.role.Role;
import com.twendeno.msauth.role.RoleRepository;
import com.twendeno.msauth.role.RoleType;
import com.twendeno.msauth.user.User;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.validation.Validation;
import com.twendeno.msauth.validation.ValidationService;
import com.twendeno.msauth.validation.dto.ValidationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void signUp(SignUpDto signUpDto) {

        if (!signUpDto.email().contains("@")) {
            throw new RuntimeException("Invalid email");
        }

        if (!signUpDto.email().contains(".")) {
            throw new RuntimeException("Invalid email");
        }

        if (signUpDto.password().length() < 12) {
            throw new RuntimeException("Password must be at least 12 characters");
        }


        Optional<User> userFound = userRepository.findByEmail(signUpDto.email());

        if (userFound.isPresent()) {
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            log.info("Email is already taken!");
            throw new RuntimeException("Email is already taken!");
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.name());
        user.setEmail(signUpDto.email());
        user.setPassword(passwordEncoder.encode(signUpDto.password()));
        user.setRole(signUpDto.role());

        if (user.getRole() != null && signUpDto.role().getName().equals(RoleType.ADMIN)) {
            signUpDto.role().setName(RoleType.ADMIN);
            user.setEnable(true);
        }

        Role role = roleRepository.findByName(signUpDto.role().getName()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        user = userRepository.save(user);

        if (user.getRole().getName().equals(RoleType.USER)) {
            this.validationService.saveValidation(user);
        }

//        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public void activation(ValidationDto validationDto) {
        Validation validation = this.validationService.getValidation(validationDto.code());

        if (validation.getExpiration().isBefore(validation.getCreation())) {
            throw new RuntimeException("Validation code has expired");
        }

        User user = this.userRepository.findById(validation.getUser().getUuid()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnable(true);
        this.userRepository.save(user);
    }

    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with" + username));
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = this.loadUserByUsername(resetPasswordDto.email());

        this.validationService.saveValidation(user);
    }

    public void newPassword(NewPasswordDto newPasswordDto) {
        User user = this.loadUserByUsername(newPasswordDto.email());
        Validation validation = this.validationService.getValidation(newPasswordDto.code());

        if (validation.getUser().getEmail().equals(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.password()));
            this.userRepository.save(user);
        }
    }
}

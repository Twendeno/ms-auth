package com.twendeno.msauth.user;

import com.twendeno.msauth.role.Role;
import com.twendeno.msauth.role.RoleRepository;
import com.twendeno.msauth.role.RoleType;
import com.twendeno.msauth.user.dto.SignUpDto;
import com.twendeno.msauth.validation.Validation;
import com.twendeno.msauth.validation.ValidationService;
import com.twendeno.msauth.validation.dto.ValidationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

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

        Role roles = roleRepository.findByName(RoleType.ROLE_USER).get();
        user.setRoles(Collections.singleton(roles));

        log.info("User: {}", user);
        user = userRepository.save(user);

        this.validationService.saveValidation(user);


//        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public void activation(ValidationDto validationDto) {
        Validation validation = this.validationService.getValidation(validationDto.getCode());

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
}

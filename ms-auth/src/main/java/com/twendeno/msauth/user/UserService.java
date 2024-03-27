package com.twendeno.msauth.user;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.user.dto.UserResponseDto;
import com.twendeno.msauth.user.dto.UserUpdateDto;
import com.twendeno.msauth.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public ApiResponse<List<UserResponseDto>> getUsers() {

        List<UserResponseDto> users = new ArrayList<>();

        Iterable<User> Users = this.userRepository.findAll();

        Users.forEach(user -> {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.getUuid().toString(),
                    user.getName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.isEnable(),
                    user.isAccountNonExpired(),
                    user.isCredentialsNonExpired(),
                    user.isEnabled(),
                    user.getRole(),
                    user.getAuthorities(),
                    user.getCreatedAt().toString(),
                    user.getUpdatedAt().toString()
            );
            users.add(userResponseDto);
        });

        return ApiResponse.<List<UserResponseDto>>builder()
                .status(HttpStatus.OK.getReasonPhrase().toLowerCase())
                .code(HttpStatus.OK.value())
                .message("Users fetched successfully")
                .data(users)
                .build();
    }

    public ApiResponse<UserResponseDto> getUserById(String uuid) {
        User user = this.userRepository.findById(UUID.fromString(uuid)).orElse(null);
        if (user == null) {
            return ApiResponse.<UserResponseDto>builder()
                    .status(HttpStatus.NOT_FOUND.getReasonPhrase().toLowerCase())
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getUuid().toString(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.isEnable(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getRole(),
                user.getAuthorities(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );

        return ApiResponse.<UserResponseDto>builder()
                .status(HttpStatus.OK.getReasonPhrase().toLowerCase())
                .code(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(userResponseDto)
                .build();
    }

    public ApiResponse<UserResponseDto> updateUser(String uuid, UserUpdateDto userUpdateDto) {
        User existingUser = this.userRepository.findById(UUID.fromString(uuid)).orElse(null);
        if (existingUser == null) {
            return ApiResponse.<UserResponseDto>builder()
                    .status(HttpStatus.NOT_FOUND.getReasonPhrase().toLowerCase())
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
        existingUser.setName(userUpdateDto.name());

        UserResponseDto response = new UserResponseDto(
                existingUser.getUuid().toString(),
                existingUser.getName(),
                existingUser.getEmail(),
                existingUser.getUsername(),
                existingUser.isEnable(),
                existingUser.isAccountNonExpired(),
                existingUser.isCredentialsNonExpired(),
                existingUser.isEnabled(),
                existingUser.getRole(),
                existingUser.getAuthorities(),
                existingUser.getCreatedAt().toString(),
                existingUser.getUpdatedAt().toString()
        );

        return ApiResponse.<UserResponseDto>builder()
                .status(HttpStatus.OK.getReasonPhrase().toLowerCase())
                .code(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(response)
                .build();
    }

    public void deleteUser(String uuid) {
        this.userRepository.deleteById(UUID.fromString(uuid));
    }
}

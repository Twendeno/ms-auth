package com.twendeno.msauth.user;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.user.dto.UserResponseDto;
import com.twendeno.msauth.user.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
@AllArgsConstructor
@EnableCaching
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "This method is used to get the clients.")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "users",unless = "#result.body.data.isEmpty()")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }


    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(this.userService.getUserById(uuid));
    }

    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable("uuid") String uuid, @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(this.userService.updateUser(uuid, userUpdateDto));
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("uuid") String uuid) {
        this.userService.deleteUser(uuid);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .message("User deleted successfully")
                .data(null)
                .build());
    }

}

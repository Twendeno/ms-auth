package com.twendeno.msauth.user;

import com.twendeno.msauth.user.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
@AllArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserService userService;


    @Operation(summary = "This method is used to get the clients.")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return this.userService.getUsers();
    }


    @GetMapping("{uuid}")
    public User getUserById(@PathVariable("uuid") String uuid) {
        return this.userService.getUserById(uuid);
    }

    @PutMapping("{uuid}")
    public User updateUser(@PathVariable("uuid") String uuid, @RequestBody UserUpdateDto userUpdateDto) {
        return this.userService.updateUser(uuid, userUpdateDto);
    }

    @DeleteMapping("{uuid}")
    public void deleteUser(@PathVariable("uuid") String uuid) {
        this.userService.deleteUser(uuid);
    }

}

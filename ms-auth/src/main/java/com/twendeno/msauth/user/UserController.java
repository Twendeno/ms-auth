package com.twendeno.msauth.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@AllArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserService userService;


    @Operation(summary = "This method is used to get the clients.")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return this.userService.getUsers();
    }


}

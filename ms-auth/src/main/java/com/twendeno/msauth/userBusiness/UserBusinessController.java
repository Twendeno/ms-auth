package com.twendeno.msauth.userBusiness;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.userBusiness.dto.CreateUserBusinessDto;
import com.twendeno.msauth.userBusiness.dto.UpdateUserBusinessDto;
import com.twendeno.msauth.userBusiness.entity.UserBusiness;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("user-business")
public class UserBusinessController {
    private final UserBusinessService userBusinessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UserBusiness>> createUserBusiness(@Valid @RequestBody CreateUserBusinessDto createUserBusinessDto) {
        return new ResponseEntity<>(userBusinessService.createUserBusiness(createUserBusinessDto), HttpStatus.CREATED);
    }

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserBusiness>> getUserBusiness(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(userBusinessService.getUserBusiness(uuid));
    }

    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserBusiness>> updateUserBusiness(@PathVariable("uuid") String uuid, @Valid @RequestBody UpdateUserBusinessDto updateUserBusinessDto) {
        return ResponseEntity.ok(userBusinessService.updateUserBusiness(uuid, updateUserBusinessDto));
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserBusiness>> deleteUserBusiness(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(userBusinessService.deleteUserBusiness(uuid));
    }

    @GetMapping("user/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<UserBusiness>>> getUserBusinessByUser(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(userBusinessService.getUserBusinessByUser(uuid));
    }

    @GetMapping("business/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<UserBusiness>>> getUserBusinessByBusiness(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(userBusinessService.getUserBusinessByBusiness(uuid));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<UserBusiness>>> getAllUserBusinesses() {
        return ResponseEntity.ok(userBusinessService.getAllUserBusiness());
    }


}

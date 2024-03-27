package com.twendeno.msauth.userLicense;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.userLicense.dto.ActivateLicenseDto;
import com.twendeno.msauth.userLicense.dto.UserLicenseDto;
import com.twendeno.msauth.userLicense.entity.UserLicense;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@RequestMapping("/user-licenses")
@PreAuthorize("hasAnyAuthority('ROLE_TENANT', 'ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
@RestController
public class UserLicenseController {

    private final UserLicenseService userLicenseService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UserLicense>> save(@Valid @RequestBody UserLicenseDto userLicenseDto) {
        return new ResponseEntity<>(this.userLicenseService.save(userLicenseDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/activate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> activateLicence(@RequestParam("file") MultipartFile file) {
        ActivateLicenseDto activateLicenseDto = new ActivateLicenseDto(file);
        return ResponseEntity.ok(this.userLicenseService.activateLicense(activateLicenseDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<UserLicense>>> getUserLicense() {
        return ResponseEntity.ok(this.userLicenseService.getUserLicense());
    }

}

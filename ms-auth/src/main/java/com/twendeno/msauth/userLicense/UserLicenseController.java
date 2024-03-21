package com.twendeno.msauth.userLicense;

import com.twendeno.msauth.userLicense.dto.ActivateLicenseDto;
import com.twendeno.msauth.userLicense.dto.UserLicenseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public void save(@RequestBody UserLicenseDto userLicenseDto) {
        this.userLicenseService.save(userLicenseDto);
    }

    @PostMapping(value = "/activate",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void activateLicence(@RequestParam("file") MultipartFile file) {
        ActivateLicenseDto activateLicenseDto = new ActivateLicenseDto(file);
        this.userLicenseService.activateLicense(activateLicenseDto);
    }

    @GetMapping
    public List<UserLicense> getUserLicense() {
        return this.userLicenseService.getUserLicense();
    }

}

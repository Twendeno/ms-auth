package com.twendeno.msauth.license;

import com.twendeno.msauth.license.dto.LicenseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("licenses")
public class LicenseController {

    private final LicenseService licenseService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<License> licenses() {
        return licenseService.getLicenses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createLicense(@RequestBody LicenseDto licenseDto) {
        licenseService.createLicense(licenseDto);
    }

}

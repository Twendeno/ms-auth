package com.twendeno.msauth.license;

import com.twendeno.msauth.license.dto.LicenseDto;
import com.twendeno.msauth.shared.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("licenses")
public class LicenseController {

    private final LicenseService licenseService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<ApiResponse<List<License>>> licenses() {
        return ResponseEntity.ok(licenseService.getLicenses());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ApiResponse<License>> createLicense(@Valid @RequestBody LicenseDto licenseDto) {
        return new ResponseEntity<>(licenseService.createLicense(licenseDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<License>> getLicenseById(@PathVariable String uuid) {
        return ResponseEntity.ok(licenseService.getLicenseById(uuid));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<License>> updateLicense(@PathVariable String uuid, @Valid @RequestBody LicenseDto licenseDto) {
        return ResponseEntity.ok(licenseService.updateLicense(uuid, licenseDto));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<String>> deleteLicense(@PathVariable String uuid) {
        licenseService.deleteLicense(uuid);
        return ResponseEntity.noContent().build();
    }

}

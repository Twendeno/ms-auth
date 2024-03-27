package com.twendeno.msauth.license;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.license.dto.LicenseDto;
import com.twendeno.msauth.shared.model.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public ApiResponse<License> createLicense(LicenseDto licenseDto) {
        License license = License.builder()
                .name(licenseDto.name())
                .price(licenseDto.price())
                .duration(licenseDto.duration())
                .build();

        license = licenseRepository.save(license);

        return ApiResponse.<License>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .message("License created successfully")
                .data(license)
                .build();
    }

    public ApiResponse<List<License>> getLicenses() {
        List<License> licenses = licenseRepository.findAll();
        return ApiResponse.<List<License>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Licenses retrieved successfully")
                .data(licenses)
                .build();
    }

    public ApiResponse<License> getLicenseById(String uuid) {
        License license =  licenseRepository.findById(UUID.fromString(uuid)).orElseThrow(()->new EntityNotFoundException("License not found"));
        return ApiResponse.<License>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("License retrieved successfully")
                .data(license)
                .build();
    }

    public ApiResponse<License> updateLicense(String uuid, LicenseDto licenseDto) {
        License license =  licenseRepository.findById(UUID.fromString(uuid)).orElseThrow(()->new EntityNotFoundException("License not found"));

        license.setName(licenseDto.name());
        license.setPrice(licenseDto.price());
        license.setDuration(licenseDto.duration());

        license = licenseRepository.save(license);

        return ApiResponse.<License>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("License updated successfully")
                .data(license)
                .build();
    }

    public ApiResponse<String> deleteLicense(String uuid) {
        License license =  licenseRepository.findById(UUID.fromString(uuid)).orElseThrow(()->new EntityNotFoundException("License not found"));

        licenseRepository.delete(license);

        return ApiResponse.<String >builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("License deleted successfully")
                .data(null)
                .build();
    }


}

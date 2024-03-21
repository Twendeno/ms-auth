package com.twendeno.msauth.license;

import com.twendeno.msauth.license.dto.LicenseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public void createLicense(LicenseDto licenseDto) {
        License license = License.builder()
                .name(licenseDto.name())
                .price(licenseDto.price())
                .duration(licenseDto.duration())
                .build();

         licenseRepository.save(license);
    }

    public List<License> getLicenses() {
        return licenseRepository.findAll();
    }



}

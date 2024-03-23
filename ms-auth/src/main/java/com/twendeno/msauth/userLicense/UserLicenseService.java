package com.twendeno.msauth.userLicense;

import com.twendeno.msauth.business.BusinessRepository;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.license.License;
import com.twendeno.msauth.license.LicenseRepository;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.userLicense.dto.ActivateLicenseDto;
import com.twendeno.msauth.userLicense.dto.UserLicenseDto;
import com.twendeno.msauth.userLicense.entity.UserLicense;
import com.twendeno.msauth.validation.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserLicenseService {

    private final UserLicenseRepository userLicenseRepository;
    private final LicenseRepository licenseRepository;
    private final BusinessRepository businessRepository;
    private final NotificationService notificationService;

    public void save(UserLicenseDto userLicenseDto) {

        // Check if the business exists
        Business business = this.businessRepository.findByName(userLicenseDto.businessName()).orElseThrow(() -> new RuntimeException("User not found"));

        // Get the user
        User user = business.getUser();

        // Check if the license exists
        License license = this.licenseRepository.findByName(userLicenseDto.licenseName()).orElseThrow(() -> new RuntimeException("License not found"));

        // Generate license key
        String licenseKey = this.generateLicenseKey();

        UserLicense userLicense = UserLicense.builder()
                .business(business)
                .license(license)
                .key(licenseKey)
                .build();

        UserLicense saved = this.userLicenseRepository.save(userLicense);

        // Send notification
        this.notificationService.sendMailForLicenseWithAttachment(user, licenseKey, license);
    }

    public List<UserLicense> getUserLicense() {
        return this.userLicenseRepository.findAll();
    }

    public void activateLicense(ActivateLicenseDto activateLicenseDto) {
        String key = this.readFile(activateLicenseDto.key());

        UserLicense userLicense = this.userLicenseRepository.findByKey(key).orElseThrow(() -> new RuntimeException("License not found"));

        // get license
        License license = userLicense.getLicense();

        if (userLicense.getExpiration() != null) {

            Date expirationDate = Date.from(userLicense.getExpiration());
            boolean before = expirationDate.before(new Date());

            if (before) {
                userLicense.setExpired(true);
                this.userLicenseRepository.save(userLicense);
                return;
            }
        }

        // set active
        userLicense.setActive(true);

        if (userLicense.getExpiration() == null || userLicense.getActivation() == null) {

            // set creation
            Instant creation = Instant.now();
            userLicense.setActivation(creation);

            // set expiration
            LocalDateTime localDateTime = LocalDateTime.ofInstant(creation, ZoneId.systemDefault());
            localDateTime = localDateTime.plusMonths(license.getDuration());

            Instant expiration = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            userLicense.setExpiration(expiration);

        }

        this.userLicenseRepository.save(userLicense);

        if (userLicense.getExpiration() == null || userLicense.getActivation() == null) {
            // Set Notification
            this.notificationService.sendActivationEmail(userLicense);
        }
    }


    private String generateLicenseKey() {
        // Generate a UUID
        UUID uuid = UUID.randomUUID();

        // Convert the UUID to a string
        String uuidString = uuid.toString();

        try {
            // Get a MessageDigest instance for SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Hash the UUID string
            byte[] hash = digest.digest(uuidString.getBytes(StandardCharsets.UTF_8));

            // Convert the hash to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Return the license key
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return content.toString();
    }
}

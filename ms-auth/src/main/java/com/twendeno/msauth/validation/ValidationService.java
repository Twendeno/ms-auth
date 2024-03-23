package com.twendeno.msauth.validation;

import com.twendeno.msauth.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    public void saveValidation(User user){

        Validation validation = new Validation();
        // set user
        validation.setUser(user);

        // set creation
        Instant creation = Instant.now();
        validation.setCreation(creation);

        // set expiration
        Instant expiration = creation.plus(10,MINUTES);
        validation.setExpiration(expiration);

        // set code
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);

        // save validation
        this.validationRepository.save(validation);

        // send email
        this.notificationService.sendEmail(validation);

    }

    public Validation getValidation(String code){
        return this.validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid code"));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredValidations(){
        this.validationRepository.deleteAllByExpirationBefore(Instant.now());
    }

}

package com.twendeno.msauth.advice;

import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public @ResponseBody ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException  e) {

        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Check your request body.");


        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            problemDetail.setProperty(fieldName, message);
        });


        return problemDetail;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public @ResponseBody ProblemDetail usernameNotFoundException(UsernameNotFoundException e) {

        log.error("BadCredentialsException: {}", e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "User not found.");
        problemDetail.setProperty("error", "not_found");

        return problemDetail;
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = LockedException.class)
    public @ResponseBody ProblemDetail lockedException(LockedException e) {

        log.error("BadCredentialsException: {}", e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Check your email to activate your account.");
        problemDetail.setProperty("error", "unauthorized");

        return problemDetail;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public @ResponseBody ProblemDetail accessDeniedException(AccessDeniedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
        problemDetail.setProperty("error", "Forbidden");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody ProblemDetail badCredentialsException(BadCredentialsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        problemDetail.setProperty("error", "Unauthorized");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public @ResponseBody ProblemDetail emailAlreadyExistsException(EmailAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Email already exists");
        problemDetail.setProperty("error", "Unauthorized");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public @ResponseBody ProblemDetail entityNotFoundException(EntityNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("error", "Not found");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler(value = RuntimeException.class)
    public @ResponseBody ProblemDetail runtimeException(RuntimeException e) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.REQUEST_TIMEOUT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {SignatureException.class, MalformedKeyException.class})
    public @ResponseBody ProblemDetail signatureException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid token");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionHandler() {
        return Map.of("error", "Unauthorized");
    }
}

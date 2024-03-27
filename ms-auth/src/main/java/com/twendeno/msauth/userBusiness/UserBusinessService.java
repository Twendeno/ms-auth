package com.twendeno.msauth.userBusiness;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.business.BusinessRepository;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.userBusiness.dto.CreateUserBusinessDto;
import com.twendeno.msauth.userBusiness.dto.UpdateUserBusinessDto;
import com.twendeno.msauth.userBusiness.entity.UserBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserBusinessService {
    private final UserBusinessRepository userBusinessRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    public ApiResponse<UserBusiness> createUserBusiness(CreateUserBusinessDto createUserBusinessDto) {

        // Get User by email
        var user = userRepository.findByEmail(createUserBusinessDto.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get Business by business name
        var business = businessRepository.findByName(createUserBusinessDto.businessName())
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));

        var userBusiness = UserBusiness.builder()
                .user(user)
                .business(business)
                .build();
        userBusiness = userBusinessRepository.save(userBusiness);

        return ApiResponse.<UserBusiness>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .message("User business created successfully")
                .data(userBusiness)
                .build();
    }

    public ApiResponse<UserBusiness> getUserBusiness(String uuid) {
        var userBusiness = userBusinessRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("User business not found"));

        return ApiResponse.<UserBusiness>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business retrieved successfully")
                .data(userBusiness)
                .build();
    }

    public ApiResponse<UserBusiness> deleteUserBusiness(String uuid) {
        var userBusiness = userBusinessRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("User business not found"));

        userBusinessRepository.delete(userBusiness);

        return ApiResponse.<UserBusiness>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business deleted successfully")
                .data(userBusiness)
                .build();
    }

    public ApiResponse<UserBusiness> updateUserBusiness(String uuid, UpdateUserBusinessDto createUserBusinessDto) {
        var userBusiness = userBusinessRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("User business not found"));

        // Get User by email
        var user = userRepository.findByEmail(createUserBusinessDto.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get Business by business name
        var business = businessRepository.findByName(createUserBusinessDto.businessName())
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));

        userBusiness.setUser(user);
        userBusiness.setBusiness(business);
        userBusiness = userBusinessRepository.save(userBusiness);

        return ApiResponse.<UserBusiness>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business updated successfully")
                .data(userBusiness)
                .build();
    }

    public ApiResponse<List<UserBusiness>> getUserBusinessByUser(String userUUID) {
        var userBusiness = userBusinessRepository.findAllByUser(UUID.fromString(userUUID))
                .orElseThrow(() -> new EntityNotFoundException("User business not found"));

        return ApiResponse.<List<UserBusiness>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business retrieved successfully")
                .data(userBusiness.toList())
                .build();
    }

    public ApiResponse<List<UserBusiness>> getUserBusinessByBusiness(String businessUUID) {
        var userBusiness = userBusinessRepository.findAllByBusiness(UUID.fromString(businessUUID))
                .orElseThrow(() -> new EntityNotFoundException("User business not found"));

        return ApiResponse.<List<UserBusiness>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business retrieved successfully")
                .data(userBusiness.toList())
                .build();
    }

    public ApiResponse<List<UserBusiness>> getAllUserBusiness() {
        var userBusiness = userBusinessRepository.findAll();

        return ApiResponse.<List<UserBusiness>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("User business retrieved successfully")
                .data(userBusiness)
                .build();
    }

}

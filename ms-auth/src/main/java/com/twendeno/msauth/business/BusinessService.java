package com.twendeno.msauth.business;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.business.dto.BusinessResponseDto;
import com.twendeno.msauth.business.dto.CreateBusinessDto;
import com.twendeno.msauth.business.dto.UpdateBusinessDto;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public ApiResponse<BusinessResponseDto> createBusiness(CreateBusinessDto createBusinessDto) {
        // Get user by email
        User user = userRepository.findByEmail(createBusinessDto.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Business business = Business.builder()
                .user(user)
                .name(createBusinessDto.name())
                .address(createBusinessDto.address())
                .city(createBusinessDto.city())
                .country(createBusinessDto.country())
                .phone(createBusinessDto.phone())
                .build();

        business = businessRepository.save(business);

        BusinessResponseDto businessResponseDto = new BusinessResponseDto(
                business.getUuid().toString(),
                business.getName(),
                business.getAddress(),
                business.getCity(),
                business.getCountry(),
                business.getPhone(),
                business.getUser(),
                business.getCreatedAt().toString(),
                business.getUpdatedAt().toString()
        );


        return ApiResponse.<BusinessResponseDto>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .message("Business created successfully")
                .data(businessResponseDto)
                .build();
    }

    public ApiResponse<List<BusinessResponseDto>> getAllBusinesses() {
        List<BusinessResponseDto> businesses = new ArrayList<>();

        businessRepository.findAll().forEach(business -> {
            BusinessResponseDto businessResponseDto = new BusinessResponseDto(
                    business.getUuid().toString(),
                    business.getName(),
                    business.getAddress(),
                    business.getCity(),
                    business.getCountry(),
                    business.getPhone(),
                    business.getUser(),
                    business.getCreatedAt().toString(),
                    business.getUpdatedAt().toString()
            );
            businesses.add(businessResponseDto);
        });

        return ApiResponse.<List<BusinessResponseDto>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Businesses fetched successfully")
                .data(businesses)
                .build();

    }

    public ApiResponse<BusinessResponseDto> getBusinessByUUID(String uuid) {
        Business business = businessRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new EntityNotFoundException("Business not found"));

        BusinessResponseDto businessResponseDto = new BusinessResponseDto(
                business.getUuid().toString(),
                business.getName(),
                business.getAddress(),
                business.getCity(),
                business.getCountry(),
                business.getPhone(),
                business.getUser(),
                business.getCreatedAt().toString(),
                business.getUpdatedAt().toString()
        );
        return ApiResponse.<BusinessResponseDto>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Business fetched successfully")
                .data(businessResponseDto)
                .build();
    }

    public ApiResponse<String> deleteBusiness(String uuid) {
        businessRepository.deleteById(UUID.fromString(uuid));
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Business deleted successfully")
                .data(null)
                .build();
    }

    public ApiResponse<BusinessResponseDto> updateBusiness(String uuid, UpdateBusinessDto updateBusinessDto) {
        Business business = businessRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new EntityNotFoundException("Business not found"));
        business.setName(updateBusinessDto.name());
        business.setAddress(updateBusinessDto.address());
        business.setCity(updateBusinessDto.city());

        business = businessRepository.save(business);

        BusinessResponseDto businessResponseDto = new BusinessResponseDto(
                business.getUuid().toString(),
                business.getName(),
                business.getAddress(),
                business.getCity(),
                business.getCountry(),
                business.getPhone(),
                business.getUser(),
                business.getCreatedAt().toString(),
                business.getUpdatedAt().toString()
        );

        return ApiResponse.<BusinessResponseDto>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Business updated successfully")
                .data(businessResponseDto)
                .build();
    }

}

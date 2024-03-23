package com.twendeno.msauth.business;

import com.twendeno.msauth.business.dto.CreateBusinessDto;
import com.twendeno.msauth.business.dto.UpdateBusinessDto;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public Business createBusiness(CreateBusinessDto createBusinessDto) {
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

        return businessRepository.save(business);
    }

    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    public Business getBusinessByUUID(String uuid) {
        return businessRepository.findById(UUID.fromString(uuid)).orElseThrow();
    }

    public void deleteBusiness(String uuid) {
        businessRepository.deleteById(UUID.fromString(uuid));
    }

    public Business updateBusiness(String uuid, UpdateBusinessDto updateBusinessDto) {
        Business business = businessRepository.findById(UUID.fromString(uuid)).orElseThrow();
        business.setName(updateBusinessDto.name());
        business.setAddress(updateBusinessDto.address());
        business.setCity(updateBusinessDto.city());
        return businessRepository.save(business);
    }

}

package com.twendeno.msauth.business;

import com.twendeno.msauth.business.dto.BusinessResponseDto;
import com.twendeno.msauth.business.dto.CreateBusinessDto;
import com.twendeno.msauth.business.dto.UpdateBusinessDto;
import com.twendeno.msauth.shared.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/business", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<BusinessResponseDto>> createBusiness(@Valid @RequestBody CreateBusinessDto createBusinessDto) {
        return new ResponseEntity<>(businessService.createBusiness(createBusinessDto), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<BusinessResponseDto>>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<BusinessResponseDto>> getBusiness(@PathVariable String uuid) {
        return ResponseEntity.ok(businessService.getBusinessByUUID(uuid));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<BusinessResponseDto>> updateBusiness(@PathVariable String uuid, @Valid @RequestBody UpdateBusinessDto updateBusinessDto) {
        return ResponseEntity.ok(businessService.updateBusiness(uuid, updateBusinessDto));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> deleteBusiness(@PathVariable String uuid) {
        return ResponseEntity.ok(businessService.deleteBusiness(uuid));
    }

}

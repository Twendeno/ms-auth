package com.twendeno.msauth.business;

import com.twendeno.msauth.business.dto.CreateBusinessDto;
import com.twendeno.msauth.business.entity.Business;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Business createBusiness(@Valid @RequestBody CreateBusinessDto createBusinessDto) {
        return businessService.createBusiness(createBusinessDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Business getBusiness(@PathVariable String uuid) {
        return businessService.getBusinessByUUID(uuid);
    }

}

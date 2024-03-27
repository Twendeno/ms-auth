package com.twendeno.msauth.business.dto;

import com.twendeno.msauth.user.entity.User;

public record BusinessResponseDto(
        String uuid,
        String name,
        String address,
        String city,
        String country,
        String phone,
        User user,
        String createdAt,
        String updatedAt
) {
}

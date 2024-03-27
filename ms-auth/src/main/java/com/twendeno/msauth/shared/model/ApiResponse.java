package com.twendeno.msauth.shared.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse <T>{
    private String status;
    private int code;
    private String message;
    private T data;
}

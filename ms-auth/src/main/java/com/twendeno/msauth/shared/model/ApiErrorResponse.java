package com.twendeno.msauth.shared.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiErrorResponse<T>{
    private String status;
    private int code;
    private String message;
    private T error;
}

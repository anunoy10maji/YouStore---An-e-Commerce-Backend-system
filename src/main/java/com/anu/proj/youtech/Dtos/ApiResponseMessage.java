package com.anu.proj.youtech.Dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ApiResponseMessage {
    private String message;
    private HttpStatus status;
    private boolean success;
}

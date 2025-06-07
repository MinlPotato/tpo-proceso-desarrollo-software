package com.uade.tpo.application.exception;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class ApiErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

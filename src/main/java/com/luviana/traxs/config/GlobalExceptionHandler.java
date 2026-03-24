package com.luviana.traxs.config;

import com.luviana.traxs.dto.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntime(RuntimeException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ApiResponse<?> handleConflict() {
        return ApiResponse.error("Data was modified by another user");
    }
}
package com.astrapay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    private List<String> message = new ArrayList<>();
    private T data;

    public ApiResponseDto(T data) {
        this.data = data;
        this.status = "OK";
    }

    public ApiResponseDto(String status, List<String> message) {
        this.status = status;
        this.message = message;

    }

    public static <T> ApiResponseDto<T> success(T data, String message) {
        ApiResponseDto<T> response = new ApiResponseDto<T>();
        response.setData(data);
        response.setStatus("OK");
        response.getMessage().add(message);
        return response;
    }


}

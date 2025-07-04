package com.example.SynClock.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;

    // Boş (no-arg) constructor (Jackson için şart!)
    public ApiResponse() {
    }

    // Sadece mesaj alan constructor
    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }

    // Mesaj ve data alan constructor
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}


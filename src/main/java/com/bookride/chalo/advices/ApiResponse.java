package com.bookride.chalo.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private T data;

    private ApiError error;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;

    public ApiResponse(){
        timeStamp = LocalDateTime.now();
    }

    public ApiResponse(ApiError error){
        this();
        this.error = error;
    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }
}
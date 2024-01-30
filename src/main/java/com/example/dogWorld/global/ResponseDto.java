package com.example.dogWorld.global;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ResponseDto {
    private String message;
    private HttpStatus status;
}

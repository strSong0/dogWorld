package com.example.dogWorld.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "user name is duplicated"),
    DIFF_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "password check is different with password"),
    PAGE_NUMBER_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "page number is wrong");

    private HttpStatus status;
    private String message;
}

package com.example.dogWorld.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController

public class ValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .reduce((error1, error2) -> error1 + ", " + error2)
                .orElse("Validation failed");

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(errorMessage);
        responseDto.setStatus(HttpStatus.BAD_REQUEST);

        // 클라이언트에게 오류 메시지 응답으로 반환
        return ResponseEntity.badRequest().body(responseDto);
    }
}

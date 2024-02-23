package com.example.dogWorld.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@ControllerAdvice
@RestController
public class FileExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParameterException(MissingServletRequestParameterException ex) {
        String errorMessage = ex.getParameterName() + "가 누락되었습니다.";

        // 클라이언트에게 오류 메시지 응답으로 반환
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(errorMessage);
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<?> handleMissingPartException(MissingServletRequestPartException ex) {
        String errorMessage = ex.getRequestPartName() + "은 필수입니다.";

        // 클라이언트에게 오류 메시지 응답으로 반환
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(errorMessage);
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(responseDto);
    }

}
package org.example.swp391.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AppException extends RuntimeException{
    private ErrorCode errorCode;


}

package org.invemotion.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.persistence.OptimisticLockException;
import org.invemotion.global.dto.ErrorResponse;
import org.invemotion.global.dto.enums.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseErrors(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType().isEnum()) {
                return ResponseEntity
                        .status(ErrorCode.INVALID_TAG.getHttpStatus())
                        .body(ErrorResponse.of(ErrorCode.INVALID_TAG));
            }
        }

        if (cause instanceof UnrecognizedPropertyException) {
            return ResponseEntity
                    .status(ErrorCode.INVALID_REQUEST.getHttpStatus())
                    .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorCode.INTERNAL_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(ErrorCode.INTERNAL_ERROR));
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLock(OptimisticLockException e) {
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(ErrorResponse.of(ErrorCode.CONCURRENT_UPDATE));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingIfMatch(MissingRequestHeaderException e) {
        if ("If-Match".equals(e.getHeaderName())) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_REQUIRED)
                    .body(ErrorResponse.of(ErrorCode.IF_MATCH_REQUIRED));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(ErrorCode.JOURNAL_ALREADY_EXISTS.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.JOURNAL_ALREADY_EXISTS));
    }
}

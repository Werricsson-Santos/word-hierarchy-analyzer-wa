package dev.werricsson.word_hierarchy_analyzer.controller.exceptions;

import dev.werricsson.word_hierarchy_analyzer.service.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.springframework.web.bind.support.WebExchangeBindException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    public ControllerExceptionHandler () {
        logger.info("ControllerExceptionHandler initialized");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<StandardError> duplicateKeyException(
            DuplicateKeyException ex, HttpServletRequest request
    ) {
        logger.error("Duplicated key exception {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(StandardError.builder()
                        .timestamp(now())
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .message(verifyDupKey(ex.getMessage()))
                        .path(request.getRequestURI())
                        .build()
                );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ValidationError> validationError (
            WebExchangeBindException ex, HttpServletRequest request
    ) {
        ValidationError error = new ValidationError(
                now(),
                request.getRequestURI(),
                BAD_REQUEST.value(),
                "Validation error",
                "Error on validation attributes"
        );

        for(FieldError x : ex.getBindingResult().getFieldErrors()) {
            error.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<StandardError> objectNotFoundException(
            ObjectNotFoundException ex, HttpServletRequest request
    ) {
        logger.error("Object not found error {}", ex.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(StandardError.builder()
                        .timestamp(now())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
                );
    }

    private String verifyDupKey(String message) {
        if(message.contains("_category_ dup key")) {
            return "Category already exists";
        }

        return "Dup key exception";
    }
}

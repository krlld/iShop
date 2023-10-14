package com.kirilldikun.ishop.exception.handler;

import com.kirilldikun.ishop.exception.CategoryAlreadyExistException;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<String> handlerCategoryAlreadyExistException(CategoryAlreadyExistException exception) {
        return ResponseEntity.status(400).body("Category already exists");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handlerCategoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.status(400).body("Category not found");
    }
}

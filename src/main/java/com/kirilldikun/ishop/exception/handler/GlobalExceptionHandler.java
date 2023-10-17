package com.kirilldikun.ishop.exception.handler;

import com.kirilldikun.ishop.exception.CategoryAlreadyExistException;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import com.kirilldikun.ishop.exception.ProductAlreadyExistsException;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
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

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handlerProductAlreadyExistException(ProductAlreadyExistsException exception) {
        return ResponseEntity.status(400).body("Product already exists");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handlerProductNotFoundException(ProductNotFoundException exception) {
        return ResponseEntity.status(400).body("Product not found");
    }
}

package com.kirilldikun.ishop.exception.handler;

import com.kirilldikun.ishop.exception.AuthorizationHeaderNotFoundException;
import com.kirilldikun.ishop.exception.CartItemAlreadyExistsException;
import com.kirilldikun.ishop.exception.CartItemNotFoundException;
import com.kirilldikun.ishop.exception.CategoryAlreadyExistException;
import com.kirilldikun.ishop.exception.CategoryNotFoundException;
import com.kirilldikun.ishop.exception.IllegalCartItemQuantityException;
import com.kirilldikun.ishop.exception.ProductAlreadyExistsException;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.exception.RoleNotFoundException;
import com.kirilldikun.ishop.exception.UserAlreadyExistsException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
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

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handlerRoleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.status(400).body("Role not found");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handlerUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(400).body("User already exists");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlerUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(400).body("User not found");
    }

    @ExceptionHandler(CartItemAlreadyExistsException.class)
    public ResponseEntity<String> handlerCartItemAlreadyExistsException(CartItemAlreadyExistsException exception) {
        return ResponseEntity.status(400).body("Cart item already exists");
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<String> handlerCartItemNotFoundException(CartItemNotFoundException exception) {
        return ResponseEntity.status(400).body("Cart item not found");
    }

    @ExceptionHandler(IllegalCartItemQuantityException.class)
    public ResponseEntity<String> handlerIllegalCartItemQuantityException(IllegalCartItemQuantityException exception) {
        return ResponseEntity.status(400).body("Illegal cart item quantity");
    }

    @ExceptionHandler(AuthorizationHeaderNotFoundException.class)
    public ResponseEntity<String> handlerAuthorizationHeaderNotFoundException(AuthorizationHeaderNotFoundException exception) {
        return ResponseEntity.status(401).body("Authorization not found");
    }
}

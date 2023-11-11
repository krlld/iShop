package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.CartItemDTO;
import com.kirilldikun.ishop.dto.CartItemResponse;
import com.kirilldikun.ishop.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponse>> findAllByUserId(@PathVariable Long userId) {
        List<CartItemResponse> cartItemDTOList = cartItemService.findAllByUserId(userId);
        return ResponseEntity.ok(cartItemDTOList);
    }

    @PostMapping
    public ResponseEntity<CartItemDTO> save(@Valid @RequestBody CartItemDTO cartItemDTO) {
        cartItemDTO = cartItemService.save(cartItemDTO);
        return ResponseEntity.ok(cartItemDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartItemService.delete(id);
        return ResponseEntity.ok().build();
    }
}

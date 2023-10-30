package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.CartItemDTO;
import com.kirilldikun.ishop.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> findAll() {
        List<CartItemDTO> cartItemDTOList = cartItemService.findAll();
        return ResponseEntity.ok(cartItemDTOList);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CartItemDTO cartItemDTO) {
        cartItemService.save(cartItemDTO);
        return ResponseEntity.ok().build();
    }
}
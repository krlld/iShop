package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.CartItemDTO;
import com.kirilldikun.ishop.entity.CartItem;
import com.kirilldikun.ishop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    private final ProductService productService;

    public List<CartItemDTO> findAll() {
        return cartItemRepository.findAll().stream().map(this::mapToCartItemDTO).toList();
    }

    public CartItem save(CartItemDTO cartItemDTO) {
        CartItem cartItem = mapToCartItem(cartItemDTO);
        return cartItemRepository.save(cartItem);
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setUserId(cartItem.getUser().getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        return cartItemDTO;
    }

    public CartItem mapToCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setUser(userService.findById(cartItemDTO.getUserId()));
        cartItem.setProduct(productService.findById(cartItemDTO.getProductId()));
        return cartItem;
    }
}

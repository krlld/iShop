package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.CartItemDTO;
import com.kirilldikun.ishop.entity.CartItem;
import com.kirilldikun.ishop.exception.CartItemAlreadyExistsException;
import com.kirilldikun.ishop.exception.CartItemNotFoundException;
import com.kirilldikun.ishop.exception.IllegalCartItemQuantityException;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
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

    public CartItemDTO save(CartItemDTO cartItemDTO) {
        if (!productService.existsById(cartItemDTO.getProductId())) {
            throw new ProductNotFoundException();
        }
        if (!userService.existsById(cartItemDTO.getUserId())) {
            throw new UserNotFoundException();
        }
        if (!cartItemRepository.existsByProductIdAndUserId(cartItemDTO.getProductId(), cartItemDTO.getUserId())) {
            if (cartItemDTO.getQuantity() <= 0) {
                throw new IllegalCartItemQuantityException();
            }
            CartItem cartItem = mapToCartItem(cartItemDTO);
            return mapToCartItemDTO(cartItemRepository.save(cartItem));
        }
        CartItem cartItem = cartItemRepository
                .findByProductIdAndUserId(cartItemDTO.getProductId(), cartItemDTO.getUserId())
                .orElseThrow(CartItemNotFoundException::new);
        if (cartItem.getQuantity() + cartItemDTO.getQuantity() <= 0) {
            throw new IllegalCartItemQuantityException();
        }
        cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
        return mapToCartItemDTO(cartItemRepository.save(cartItem));
    }

    public void delete(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException();
        }
        cartItemRepository.deleteById(id);
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .userId(cartItem.getUser().getId())
                .productId(cartItem.getProduct().getId())
                .build();
    }

    public CartItem mapToCartItem(CartItemDTO cartItemDTO) {
        return CartItem.builder()
                .id(cartItemDTO.getId())
                .quantity(cartItemDTO.getQuantity())
                .user(userService.findById(cartItemDTO.getUserId()))
                .product(productService.findById(cartItemDTO.getProductId()))
                .build();
    }
}

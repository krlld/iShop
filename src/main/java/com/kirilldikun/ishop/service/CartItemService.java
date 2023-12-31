package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.CartItemDTO;
import com.kirilldikun.ishop.dto.CartItemResponse;
import com.kirilldikun.ishop.entity.CartItem;
import com.kirilldikun.ishop.exception.CartItemNotFoundException;
import com.kirilldikun.ishop.exception.IllegalCartItemQuantityException;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    private final ProductService productService;

    public List<CartItem> findAllByUserId(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return cartItemRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "id"));
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

    public void clear(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        cartItemRepository.deleteAllByUserId(userId);
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

    public CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .userId(cartItem.getUser().getId())
                .product(productService.mapToProductDTO(cartItem.getProduct()))
                .build();
    }
}

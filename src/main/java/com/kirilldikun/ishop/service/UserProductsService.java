package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.UserProductsResponse;
import com.kirilldikun.ishop.entity.Product;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.CartItemRepository;
import com.kirilldikun.ishop.repository.FavoriteItemRepository;
import com.kirilldikun.ishop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductsService {

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final FavoriteItemRepository favoriteItemRepository;

    private final UserService userService;

    private final ImageService imageService;

    public Page<UserProductsResponse> findAllProductsWithUserInfo(Long userId, Pageable pageable) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> mapToUserProductsResponse(product, userId));
    }

    public UserProductsResponse mapToUserProductsResponse(Product product, Long userId) {
        return UserProductsResponse.builder()
                .userId(userId)
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .images(product.getImages().stream().map(imageService::mapToImageDTO).toList())
                .category(product.getCategory().getName())
                .isInCart(cartItemRepository.existsByProductIdAndUserId(product.getId(), userId))
                .isInFavorite(favoriteItemRepository.existsByProductIdAndUserId(product.getId(), userId))
                .build();
    }
}

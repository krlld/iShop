package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.FavoriteItemDTO;
import com.kirilldikun.ishop.dto.FavoriteItemResponse;
import com.kirilldikun.ishop.entity.FavoriteItem;
import com.kirilldikun.ishop.exception.ProductNotFoundException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.FavoriteItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteItemService {

    private final FavoriteItemRepository favoriteItemRepository;

    private final UserService userService;

    private final ProductService productService;

    public List<FavoriteItemResponse> findAllByUserId(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return favoriteItemRepository.findAllByUserId(userId).stream()
                .map(this::mapToFavoriteItemResponse).toList();
    }

    public void changeFavoriteStatus(FavoriteItemDTO favoriteItemDTO) {
        if (!userService.existsById(favoriteItemDTO.getUserId())) {
            throw new UserNotFoundException();
        }
        if (!productService.existsById(favoriteItemDTO.getProductId())) {
            throw new ProductNotFoundException();
        }
        Optional<FavoriteItem> favoriteItemOptional = favoriteItemRepository
                .findByUserIdAndProductId(favoriteItemDTO.getUserId(), favoriteItemDTO.getProductId());
        if (favoriteItemOptional.isPresent()) {
            favoriteItemRepository.deleteById(favoriteItemOptional.get().getId());
            return;
        }
        FavoriteItem favoriteItem = mapToFavoriteItem(favoriteItemDTO);
        favoriteItemRepository.save(favoriteItem);
    }

    public FavoriteItem mapToFavoriteItem(FavoriteItemDTO favoriteItemDTO) {
        return FavoriteItem.builder()
                .id(favoriteItemDTO.getId())
                .user(userService.findById(favoriteItemDTO.getUserId()))
                .product(productService.findById(favoriteItemDTO.getProductId()))
                .build();
    }

    public FavoriteItemResponse mapToFavoriteItemResponse(FavoriteItem favoriteItem) {
        return FavoriteItemResponse.builder()
                .id(favoriteItem.getId())
                .userId(favoriteItem.getUser().getId())
                .product(productService.mapToProductDTO(favoriteItem.getProduct()))
                .build();
    }
}

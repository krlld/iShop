package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProductsResponse {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String description;

    @NotNull
    private List<ImageDTO> images;

    @NotNull
    private String category;

    @NotNull
    private boolean isInCart;

    @NotNull
    private boolean isInFavorite;
}

package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private Long id;

    @NotNull
    private int quantity;

    @NotNull
    private Long userId;

    @NotNull
    private ProductDTO product;
}

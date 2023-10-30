package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDTO {

    private Long id;

    @NotNull
    private int quantity;

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;
}

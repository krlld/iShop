package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String description;

    @NotNull
    private List<ImageDTO> images;

    @NotNull
    private Long categoryId;
}

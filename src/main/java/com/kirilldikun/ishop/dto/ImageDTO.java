package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImageDTO {
    private Long id;

    @NotNull
    private String url;

    private Long productId;
}

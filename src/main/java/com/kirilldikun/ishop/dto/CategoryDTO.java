package com.kirilldikun.ishop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotNull
    private String name;
}

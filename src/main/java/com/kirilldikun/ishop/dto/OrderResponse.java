package com.kirilldikun.ishop.dto;

import com.kirilldikun.ishop.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;

    @NotNull
    @NotBlank
    private String deliveryAddress;

    @NotNull
    private LocalDate date;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private Long userId;

    @NotNull
    private List<OrderItemDTO> orderItems;

}

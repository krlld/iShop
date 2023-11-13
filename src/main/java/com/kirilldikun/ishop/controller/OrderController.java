package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.OrderRequest;
import com.kirilldikun.ishop.dto.OrderResponse;
import com.kirilldikun.ishop.entity.Order;
import com.kirilldikun.ishop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrderFromUserCart(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrderFromUserCart(orderRequest);
        return ResponseEntity.ok(orderService.mapToOrderResponse(order));
    }
}

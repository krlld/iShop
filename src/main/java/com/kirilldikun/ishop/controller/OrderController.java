package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.OrderRequest;
import com.kirilldikun.ishop.dto.OrderResponse;
import com.kirilldikun.ishop.entity.Order;
import com.kirilldikun.ishop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> findAllByUserId(@PathVariable Long userId) {
        List<OrderResponse> orderResponses = orderService.findAllByUserId(userId);
        return ResponseEntity.ok(orderResponses);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrderFromUserCart(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrderFromUserCart(orderRequest);
        return ResponseEntity.ok(orderService.mapToOrderResponse(order));
    }
}

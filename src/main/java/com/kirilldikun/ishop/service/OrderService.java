package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.OrderItemDTO;
import com.kirilldikun.ishop.dto.OrderRequest;
import com.kirilldikun.ishop.dto.OrderResponse;
import com.kirilldikun.ishop.entity.CartItem;
import com.kirilldikun.ishop.entity.Order;
import com.kirilldikun.ishop.entity.OrderItem;
import com.kirilldikun.ishop.entity.OrderStatus;
import com.kirilldikun.ishop.exception.EmptyCartException;
import com.kirilldikun.ishop.exception.OrderNotFoundException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.OrderItemRepository;
import com.kirilldikun.ishop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private final CartItemService cartItemService;

    @Transactional
    public Order createOrderFromUserCart(OrderRequest orderRequest) {
        if (!userService.existsById(orderRequest.getUserId())) {
            throw new UserNotFoundException();
        }
        List<CartItem> cartItems = cartItemService.findAllByUserId(orderRequest.getUserId());
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }
        Order order = Order.builder()
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .date(LocalDate.now())
                .orderStatus(OrderStatus.IN_PROGRESS)
                .user(userService.findById(orderRequest.getUserId()))
                .build();
        order = orderRepository.save(order);
        Long orderId = order.getId();
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> mapToOrderItem(cartItem, orderId))
                .toList();
        orderItems = orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);
        cartItemService.clear(orderRequest.getUserId());
        return order;
    }

    public OrderItem mapToOrderItem(CartItem cartItem, Long orderId) {
        return OrderItem.builder()
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice())
                .product(cartItem.getProduct())
                .order(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new))
                .build();
    }

    public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .orderId(orderItem.getOrder().getId())
                .build();
    }

    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .deliveryAddress(order.getDeliveryAddress())
                .date(order.getDate())
                .orderStatus(order.getOrderStatus())
                .userId(order.getUser().getId())
                .orderItems(order.getOrderItems().stream().map(this::mapToOrderItemDTO).toList())
                .build();
    }
}

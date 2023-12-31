package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.OrderItemDTO;
import com.kirilldikun.ishop.dto.OrderRequest;
import com.kirilldikun.ishop.dto.OrderResponse;
import com.kirilldikun.ishop.entity.CartItem;
import com.kirilldikun.ishop.entity.Order;
import com.kirilldikun.ishop.entity.OrderItem;
import com.kirilldikun.ishop.entity.OrderStatus;
import com.kirilldikun.ishop.exception.EmptyCartException;
import com.kirilldikun.ishop.exception.OrderAlreadyDeliveredException;
import com.kirilldikun.ishop.exception.OrderNotFoundException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.OrderItemRepository;
import com.kirilldikun.ishop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private final CartItemService cartItemService;

    private final ProductService productService;

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

    public List<OrderResponse> findAllByUserId(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        List<Order> orders = orderRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "id"));
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    public void confirmDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if(order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            throw new OrderAlreadyDeliveredException();
        }
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
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
                .productDTO(productService.mapToProductDTO(orderItem.getProduct()))
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
                .total(order.getOrderItems().stream()
                        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                        .reduce(BigDecimal::add)
                        .orElseThrow())
                .build();
    }
}

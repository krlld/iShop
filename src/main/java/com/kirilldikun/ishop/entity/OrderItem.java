package com.kirilldikun.ishop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    boolean existsByProductIdAndUserId(Long productId, Long userId);

    Optional<CartItem> findByProductIdAndUserId(Long productId, Long userId);

    List<CartItem> findAllByUserId(Long userId);
}

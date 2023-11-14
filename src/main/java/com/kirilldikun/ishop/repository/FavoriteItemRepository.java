package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.FavoriteItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {

    boolean existsByProductIdAndUserId(Long productId, Long userId);

    Optional<FavoriteItem> findByUserIdAndProductId(Long userId, Long productId);

    List<FavoriteItem> findAllByUserId(Long userId, Sort sort);
}

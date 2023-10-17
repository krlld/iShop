package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Product findByName(String name);
}

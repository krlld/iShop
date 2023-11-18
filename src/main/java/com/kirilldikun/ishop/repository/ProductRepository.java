package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    Page<Product> findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description, Pageable pageable);
}

package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsById(Long id);

    boolean existsByName(String name);
}

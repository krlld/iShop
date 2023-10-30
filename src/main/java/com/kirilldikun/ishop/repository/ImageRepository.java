package com.kirilldikun.ishop.repository;

import com.kirilldikun.ishop.entity.Image;
import com.kirilldikun.ishop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProduct(Product product);
}

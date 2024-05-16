package com.edu.authen.repository;

import com.edu.authen.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    List<ProductImage> findAllByProductId(Long id);
}

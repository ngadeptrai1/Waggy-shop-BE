package com.edu.authen.service.impl;

import com.edu.authen.model.Product;
import com.edu.authen.model.ProductImage;
import com.edu.authen.repository.ProductImageRepository;
import com.edu.authen.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public List<ProductImage> findByProductId(Long id) {
        return productImageRepository.findAllByProductId(id);
    }
}

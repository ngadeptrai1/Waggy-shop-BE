package com.edu.authen.service;

import com.edu.authen.model.Product;
import com.edu.authen.model.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductImageService {
    ProductImage save (ProductImage productImage );
    List<ProductImage> findByProductId(Long id);
}

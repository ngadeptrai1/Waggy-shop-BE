package com.edu.authen.service;

import com.edu.authen.DTO.ProductDTO;
import com.edu.authen.model.Product;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface ProductService {
    ProductResponse saveProduct(ProductDTO product  ) throws IOException;
    ProductResponse updateProduct(ProductDTO product , Long id);
    Page<ProductResponse> findAll (Map<String, Object> filterCriteria, Pageable pageable);
//    Page<ProductResponse> productShop (Pageable pageable,String cateName,String brandName,);
    ProductResponse findById(Long id);
}

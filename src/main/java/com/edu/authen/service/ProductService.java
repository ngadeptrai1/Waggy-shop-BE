package com.edu.authen.service;

import com.edu.authen.DTO.ProductDTO;
import com.edu.authen.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Product saveProduct(ProductDTO product  ) throws IOException;
    Product updateProduct(ProductDTO product , Long id);
    List<Product> findAll ();
    Product findById(Long id);
}

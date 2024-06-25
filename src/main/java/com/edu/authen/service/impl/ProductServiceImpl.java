package com.edu.authen.service.impl;

import com.edu.authen.DTO.ProductDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.exceptions.IdNotFoundException;
import com.edu.authen.model.Brand;
import com.edu.authen.model.Category;
import com.edu.authen.model.Product;
import com.edu.authen.model.ProductImage;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileHandleService fileService;
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class,IOException.class,NullPointerException.class})
    public Product saveProduct(ProductDTO product) throws IOException, IdNotFoundException {

        Brand brand = brandService.findById(product.getBrandId());
        Category category = categoryService.findById(product.getCategoryId());

        var newProduct = new Product().builder()
                .name(product.getName())
                .activate(product.isActivate())
                .category(category)
                .brand(brand)
                .description(product.getDescription())
                .thumbnail("")
                .originPrice(product.getOriginPrice())
                .salePrice(product.getSalePrice())
                .quantity(product.getQuantity());

        Product entityProduct = productRepository.save(newProduct.build());
        fileService.uploadThumbnail(product.getThumbnail(),entityProduct);
        List<ProductImage> listImages = new LinkedList<>();

        if( product.getProductImages() != null || !product.getProductImages().isEmpty()){
            product.getProductImages().forEach(
                    image -> {
                        try {
                            ProductImage prImage = ProductImage.builder()
                                    .name("")
                                    .product(entityProduct).build();
                                    fileService.uploadCoudary(image,prImage);
                            while (prImage.getName().isBlank()){
                                Thread.onSpinWait();
                            }
                                    prImage = productImageService.save(prImage);
                                  listImages.add(prImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }else{
            throw new DataNotFoundException("Images can not be null or empty !");
        }
        while (entityProduct.getThumbnail().isBlank()){
            Thread.onSpinWait();
        }
        entityProduct.setProductImages(listImages);
        return entityProduct;
    }

    @Override
    public Product updateProduct(ProductDTO product, Long id) {
        Product entity = productRepository.findById(id).orElseThrow(
                () ->  new DataNotFoundException("Not found product with id  " + id)
        );
        entity.setActivate(product.isActivate());
        entity.setDescription(product.getDescription());
        entity.setName(product.getName());
        entity.setQuantity(product.getQuantity());
        entity.setOriginPrice(product.getOriginPrice());
        entity.setSalePrice(product.getSalePrice());
        return productRepository.save(entity);
    }
    @Override
    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () ->  new DataNotFoundException("Not found product with id  " + id));
    }
}

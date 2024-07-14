package com.edu.authen.service.impl;

import com.edu.authen.DTO.OrderDTO;
import com.edu.authen.DTO.ProductDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.exceptions.FileException;
import com.edu.authen.exceptions.IdNotFoundException;
import com.edu.authen.model.*;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.response.ProductImageResponse;
import com.edu.authen.response.ProductResponse;
import com.edu.authen.service.*;
import com.edu.authen.ultis.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileHandleService fileService;
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ModelMapper modelMapper;
    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class,IOException.class,NullPointerException.class})
    public ProductResponse saveProduct(ProductDTO product) throws IOException, IdNotFoundException {

        MultipartFile thumbnail = product.getThumbnail();
        if(thumbnail.getSize() > 10* 1024*1024) { // > 10mb
            throw new FileException("File too large , Maximum size is 10MB", HttpStatus.PAYLOAD_TOO_LARGE);
        }
        if(thumbnail.getContentType()== null || !thumbnail.getContentType().startsWith("image/")){ // check is image
            throw new FileException("File must be an image",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        if(product.getProductImages()!= null || !product.getProductImages().isEmpty()){
            for (MultipartFile image :
                    product.getProductImages()) {
                if( image.getSize() > 10* 1024*1024) { // > 10mb
                    throw new FileException("File too large , Maximum size is 10MB", HttpStatus.PAYLOAD_TOO_LARGE);
                }
                if(image.getContentType()== null || !image.getContentType().startsWith("image/")){ // check is image
                    throw new FileException("File must be an image",
                            HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
            }
        }
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
                        ProductImage prImage = ProductImage.builder()
                                .name("")
                                .product(entityProduct).build();
                        try {
                            fileService.uploadCoudary(image,prImage);
                            listImages.add(prImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }else{
            throw new DataNotFoundException("Images can not be null or empty !");
        }
        listImages.forEach(image->{
            while (image.getName().isBlank()){
                Thread.onSpinWait();
            }
        });
        while (entityProduct.getThumbnail().isBlank()){
            Thread.onSpinWait();
        }
        entityProduct.setProductImages(productImageService.saveAll(listImages));
        return ProductResponse.fromProduct(entityProduct);
    }

    @Override
    public ProductResponse updateProduct(ProductDTO product, Long id) {
        Product entity = productRepository.findById(id).orElseThrow(
                () ->  new DataNotFoundException("Not found product with id  " + id)
        );
        entity.setActivate(product.isActivate());
        entity.setDescription(product.getDescription());
        entity.setName(product.getName());
        entity.setQuantity(product.getQuantity());
        entity.setOriginPrice(product.getOriginPrice());
        entity.setSalePrice(product.getSalePrice());
        return ProductResponse.fromProduct(productRepository.save(entity) ) ;
    }
    @Override
    public Page<ProductResponse> findAll(Map<String, Object> filterCriteria, Pageable pageable) {
        ProductSpecification spec = new ProductSpecification(filterCriteria);
        Page<ProductResponse> page =   productRepository.findAll(spec,pageable).map(product -> {
            return ProductResponse.fromProduct(product);
        });
        return page;
    }



    @Override
    public ProductResponse findById(Long id) {
        return ProductResponse.fromProduct(productRepository.findById(id).orElseThrow(
                () ->  new DataNotFoundException("Not found product with id  " + id)) ) ;
    }

}

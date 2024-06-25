package com.edu.authen.controller;

import com.edu.authen.DTO.ProductDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.model.Product;
import com.edu.authen.response.DataResponse;
import com.edu.authen.response.ProductImageResponse;
import com.edu.authen.response.ProductResponse;
import com.edu.authen.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam("page") Optional<Integer> pageNum,
                                            @RequestParam("size") Optional<Integer> size,
                                            @RequestParam("sort")Optional<String> sort){
        Pageable page = PageRequest.of(pageNum.orElse(0),size.orElse(5), Sort.by(sort.orElse("id")).descending());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(page).stream().map(product -> {
                return  changeModel(product);
            }));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(changeModel( productService.findById(id)));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@Valid @ModelAttribute ProductDTO product
            , BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                throw new DataInvalidException( "Err",errs);
            }
            MultipartFile thumbnail = product.getThumbnail();
            if(thumbnail.getSize() > 10* 1024*1024) { // > 10mb
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body( DataResponse.builder()
                                .message( "File too large , Maximum size is 10MB")
                                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .timestamp(ZonedDateTime.now())
                                .build());
            }
            if(thumbnail.getContentType()== null || !thumbnail.getContentType().startsWith("image/")){ // check is image
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body( DataResponse.builder()
                                .message("File must be an image")
                                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .timestamp(ZonedDateTime.now())
                                .build());

            }
            if(product.getProductImages()!= null || !product.getProductImages().isEmpty()){
                for (MultipartFile image :
                        product.getProductImages()) {
                    if( image.getSize() > 10* 1024*1024) { // > 10mb
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body( DataResponse.builder()
                                        .message( "File too large , Maximum size is 10MB")
                                        .status(HttpStatus.PAYLOAD_TOO_LARGE)
                                        .timestamp(ZonedDateTime.now())
                                        .build());                    }
                    if(image.getContentType()== null || !image.getContentType().startsWith("image/")){ // check is image
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body( DataResponse.builder()
                                        .message("File must be an image")
                                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                        .timestamp(ZonedDateTime.now())
                                        .build());
                    }
                }
            }
            Product  newProduct = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            changeModel(newProduct)
                    );
        }
        catch (IOException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some thing went wrong , please try again ");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody ProductResponse product,
            BindingResult result,
            @PathVariable Long id ){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            throw new DataInvalidException( "Err",errs);
        }
        ProductDTO productDTO = ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .activate(product.isActivate())
                .brandId(product.getBrandId())
                .categoryId(product.getCategoryId())
                .quantity(product.getQuantity())
                .originPrice(product.getOriginPrice())
                .salePrice(product.getSalePrice())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        changeModel(productService.updateProduct(productDTO,id))
                );
    }
    private ProductResponse changeModel(Product product){
        return ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .activate(product.isActivate())
                                .thumbnail(product.getThumbnail())
                                .brandId(product.getBrand().getId())
                                .categoryId(product.getCategory().getId())
                                .quantity(product.getQuantity())
                                .originPrice(product.getOriginPrice())
                                .salePrice(product.getSalePrice())
                                .productImages(product.getProductImages().stream().map(productImage -> {
                                    return ProductImageResponse.builder()
                                            .id(productImage.getId())
                                            .name(productImage.getName())
                                            .build();
                                }).collect(Collectors.toList()))
                                .build();

    }
}

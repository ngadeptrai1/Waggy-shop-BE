package com.edu.authen.response;

import com.edu.authen.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private boolean activate ;
    private String thumbnail;
    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;
    @JsonProperty("origin_price")
    private Double originPrice;
    @JsonProperty("sale_price")
    private Double salePrice;
    @JsonProperty("category_id")
    private int categoryId;
    @JsonProperty("brand_id")
    private int brandId;

    public static ProductResponse fromProduct(Product product){
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

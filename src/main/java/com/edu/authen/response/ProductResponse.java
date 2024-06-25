package com.edu.authen.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

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
}

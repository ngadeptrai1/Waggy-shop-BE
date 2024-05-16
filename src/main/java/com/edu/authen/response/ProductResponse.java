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
    @NotEmpty(message = "Product name cannot be empty")
    @NotNull(message = "Product name cannot be null")
    @Size(min = 6 , max = 200, message = "Product name must between 6 - 200 characters")
    private String name;
    @NotEmpty(message = "Product description cannot be empty")
    @NotNull(message = "Product description cannot be null")
    @Size(min = 6 , max = 500, message = "Product description must between 6 - 500 characters")
    private String description;
    @Min(value = 0, message = "Product quantity must be greater than or equal to 0 ")
    @Max(value = 1000 ,message = "Product quantity must be less than 1000 " )
    private int quantity;
    private boolean activate ;
    private String thumbnail;
    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;
    @JsonProperty("origin_price")
    @NotNull(message = "Product origin price cannot be null")
    @Min(value = 0, message = "Product origin price must be greater than or equal to 0 ")
    @Max(value = 10000000 ,message = "Product origin price must be less than 10 000 000 " )
    private BigDecimal originPrice;
    @JsonProperty("sale_price")
    @NotNull(message = "Product  sale price cannot be null")
    @Min(value = 0, message = "Product sale price must be greater than or equal to 0 ")
    @Max(value = 10000000 ,message = "Product sale price must be less than 10 000 000 " )
    private BigDecimal salePrice;
    @JsonProperty("category_id")
    @NotNull(message = "Category id  cannot be null")
    @Min(value = 0, message = "Category id is not valid ")
    private int categoryId;
    @JsonProperty("brand_id")
    @NotNull(message = "Brand id cannot be null")
    @Min(value = 0, message = "Brand id is not valid ")
    private int brandId;
}

package com.edu.authen.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ProductImageResponse {
    private Long id;
    private String name ;
}

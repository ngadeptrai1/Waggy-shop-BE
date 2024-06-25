package com.edu.authen.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty(value = "full_name")
    private String fullName;
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    private String address;
    private String note;
    private String status;
    @JsonProperty(value = "total_money")
    private Double totalMoney;
    @JsonProperty(value = "discount_money")
    private Float discountMoney;
    @JsonProperty("quantity_product")
    private int quantityProduct;
    @JsonProperty("attribute_discount")
    private Float attributeDiscount;
    private String code;

    @JsonProperty(value = "cart_items")
    private List<OrderDetailResponse> orderDetails;

}

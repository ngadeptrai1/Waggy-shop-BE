package com.edu.authen.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailResponse {
    private Long id;

    @JsonProperty(value = "product_id")
    private Long productId;


    private int quantity;

    private Long price;

    @JsonProperty(value = "total_money")
    private Double totalMoney;

    @JsonProperty("order_id")
    private Long orderId;
}

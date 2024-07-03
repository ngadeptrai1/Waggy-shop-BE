package com.edu.authen.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1 , message = "User id is not valid")
    private Long userId;
    @JsonProperty(value = "full_name")
    @Size(min = 6 , max = 100 ,message = "full name must be 6 - 100 character")
    @NotEmpty( message = "Full name can not be empty")
    @NotNull(message = "Full name can not be null")
    private String fullName;
    @JsonProperty(value = "phone_number")
    @Size(min = 6 , max = 20 ,message = "phone number must be 6 - 20 character")
    @NotBlank(message = "Phone number can not blank")
    @NotEmpty(message = "Phone number can not be empty")
    private String phoneNumber;
    @Size(min = 6 , max = 200 ,message = "Address number must be 6 - 200 character")
    @NotNull(message = "Address can not be null")
    @NotEmpty(message = "Address can not be empty")
    private String address;
    private String note;
    private String status;
    @JsonProperty(value = "total_money")
    private Float totalMoney;
    @JsonProperty(value = "discount_money")
    private Float discountMoney;
    @JsonProperty("quantity_product")
    private int quantityProduct;
    @JsonProperty("attribute_discount")
    private Float attributeDiscount;
    private String code;
    @Size(min = 1, max = 50, message = "List size must be between 1 and 50 elements")
    @JsonProperty(value = "order_details")
    @Valid
    private List<OrderDetailDTO> orderDetails;

}

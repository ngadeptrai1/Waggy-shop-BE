package com.edu.authen.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Account Name cannot be empty")
    @NotNull(message = "Account name cannot be null")
    @Size(min = 6 , max = 12, message = "Account name between 6 - 12 characters")
    @JsonProperty(value = "account_name")
    private  String accountName ;

    @NotEmpty(message = "Password cannot be empty")
    @NotNull(message = "Password  cannot be null")
    @Size(min = 6 , max = 12, message = "`Password must be between 6 - 12 character")
    private  String password;

    @Email(message = "Email is not valid")
    private  String email;


    @NotBlank(message = "Name cannot be empty")
    @NotNull(message = "Name  cannot be null")
    @Size(min = 6 , max = 12, message = "`Name must be between 10 - 50 character")
    @JsonProperty(value = "full_name")
    private  String fullName;
}

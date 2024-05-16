package com.edu.authen.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationRequest {
    @NotEmpty(message = "Account Name cannot be empty")
    @NotNull(message = "Account name cannot be null")
    @Size(min = 6 , max = 12, message = "Account name between 6 - 12 characters")
    @JsonProperty(value = "account_name")
    private String accountName;

    @NotEmpty(message = "Password cannot be empty")
    @NotNull(message = "Password  cannot be null")
    @Size(min = 6 , max = 12, message = "`Password must be between 6 - 12 character")
    private String password;
}

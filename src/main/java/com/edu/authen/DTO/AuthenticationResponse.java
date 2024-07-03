package com.edu.authen.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    @JsonProperty("user_name")
    private String userName;
    private String token;
    private String email;
}

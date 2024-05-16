package com.edu.authen.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BrandDTO {
    private Integer id;
    @NotEmpty(message = "Brand name cannot be empty")
    @NotNull(message = "Brand name cannot be null")
    @Size(min = 6 , max = 200, message = "Brand name must between 6 - 200 characters")
    private String name;
    @NotEmpty(message = "Brand description cannot be empty")
    @NotNull(message = "Brand description cannot be null")
    @Size(min = 6 , max = 500, message = "Brand description must between 6 - 500 characters")
    private String description;
    private boolean activate = true;
}

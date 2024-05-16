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
public class CategoryDTO {
    private Integer id;
    @NotEmpty(message = "Category name cannot be empty")
    @NotNull(message = "Category name cannot be null")
    @Size(min = 6 , max = 200, message = "Category name must between 6 - 200 characters")
    private String name;
    @NotEmpty(message = "Category description cannot be empty")
    @NotNull(message = "Category description cannot be null")
    @Size(min = 6 , max = 500, message = "Category description must between 6 - 500 characters")
    private String description;
    private boolean activate = true;

}

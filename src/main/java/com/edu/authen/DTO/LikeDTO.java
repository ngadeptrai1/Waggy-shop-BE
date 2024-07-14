package com.edu.authen.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LikeDTO {
    private long userId;
    private long productId;
}

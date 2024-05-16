package com.edu.authen.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    private String description;

    private int quantity;

    private boolean activate;

    private String thumbnail;

    private BigDecimal originPrice ;

    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "category_id" , referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id" , referencedColumnName = "id")
    private Brand brand;

    @OneToMany(mappedBy="product",fetch = FetchType.LAZY)
    private List<ProductImage> productImages;
}

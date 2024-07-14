package com.edu.authen.repository;

import com.edu.authen.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT p FROM Product p where p.activate= true AND p.category.activate = true AND p.quantity > 0  AND p.brand.activate = true AND p.category.name like %:cateName% AND p.brand.name like %:brandName% and p.salePrice between :min AND :max AND p.name like %:name%")
//    Page<Product>findProducts(@Param("cateName") String cateName, @Param("brandName")String brandName,@Param("min") Double min,@Param("max")Double max,@Param("name") String name);

}

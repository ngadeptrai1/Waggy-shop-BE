package com.edu.authen.repository;

import com.edu.authen.model.Like;
import com.edu.authen.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
//    @Query("SELECT l.product from Like l where l.user.id=:id")
    Page<Like>findLikesByUserId ( Long id, Pageable pageable);




    @Query("SELECT l FROM Like l where l.user.id = :userId and l.product.id = :productId")
    Optional<Like> findLike(@Param("userId") Long userId,@Param("productId") Long productId);
}

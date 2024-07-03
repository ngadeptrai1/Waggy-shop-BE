package com.edu.authen.repository;

import com.edu.authen.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findLikesByUser_Id(Long id);
    Optional<Like> findByUser_IdAndProduct_Id(Long userId,Long productId);
}

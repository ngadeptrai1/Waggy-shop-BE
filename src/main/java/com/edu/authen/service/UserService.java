package com.edu.authen.service;

import com.edu.authen.DTO.LikeDTO;
import com.edu.authen.model.Like;
import com.edu.authen.model.User;
import com.edu.authen.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

  List<User> findAll(Pageable pageable);

  Page<ProductResponse> getProductsUserLike(Long userId,Pageable pageable );
  void likeProduct(LikeDTO like);
}

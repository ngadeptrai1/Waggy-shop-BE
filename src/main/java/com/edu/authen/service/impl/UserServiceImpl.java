package com.edu.authen.service.impl;

import com.edu.authen.DTO.LikeDTO;
import com.edu.authen.exceptions.DataNotFoundException;

import com.edu.authen.model.Like;
import com.edu.authen.model.Product;
import com.edu.authen.model.User;
import com.edu.authen.repository.LikeRepository;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.repository.UserRepository;
import com.edu.authen.response.ProductResponse;
import com.edu.authen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userDao;

    private final LikeRepository likeRepository;

    private final ProductRepository productRepository;


    @Override
    public List<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable).getContent();
    }



    @Override
    public Page<ProductResponse> getProductsUserLike(Long userId, Pageable pageable) {
        Page<ProductResponse> page = likeRepository.findLikesByUserId(userId,pageable).map(like -> {
            return ProductResponse.fromProduct(like.getProduct());
        });

        return page;

    }

    @Override
    public void likeProduct(LikeDTO likeDTO) {
       Optional<Like> like =  likeRepository.findLike(likeDTO.getUserId() , likeDTO.getProductId());
        if(like.isEmpty()){
          Like likeNew =  new Like();
            Product product = productRepository.findById(likeDTO.getProductId()).orElseThrow(
                    ()->new DataNotFoundException("Not found product with id  = "+ likeDTO.getProductId()));
            User user = userDao.findById(likeDTO.getUserId()).orElseThrow(
                    ()-> new DataNotFoundException("Not found user with id = "+likeDTO.getUserId()));
            likeNew.setUser(user);
            likeNew.setProduct(product);
            likeNew.setLikeDate(LocalDateTime.now());
            likeRepository.save(likeNew);

        }else{
            likeRepository.delete(like.get());
        }
    }



}

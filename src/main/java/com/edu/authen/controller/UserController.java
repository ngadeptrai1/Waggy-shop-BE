package com.edu.authen.controller;

import com.edu.authen.DTO.LikeDTO;
import com.edu.authen.DTO.Respones;
import com.edu.authen.model.Like;
import com.edu.authen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> findAll(
                                     @RequestParam("page")Optional<Integer> pageNum){
        Pageable page = PageRequest.of(pageNum.orElse(0),9, Sort.by("id").descending());

        return ResponseEntity.status(HttpStatus.OK).body(
               userService.findAll(page)
        );
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<?> getLikeByUserId(@PathVariable("id") Long id,
                                             @RequestParam("page")Optional<Integer> pageNum){
        Pageable page = PageRequest.of(pageNum.orElse(0),9, Sort.by("id").descending());

        return ResponseEntity.ok(  userService.getProductsUserLike(id,page));
    }

    @PostMapping("/like")
    public ResponseEntity<?> updateLike(@RequestBody LikeDTO like){
            userService.likeProduct(like);
        return ResponseEntity.ok(like);
    }
}

package com.edu.authen.controller;

import com.edu.authen.DTO.Respones;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/test")
public class UserController {
    @GetMapping("")
    public ResponseEntity<?> test(){
        System.out.println("ok");
        return ResponseEntity.status(HttpStatus.OK).body(
                Respones.builder()
                        .message("ok")
                        .status(200)
                        .build()
        );
    }
}

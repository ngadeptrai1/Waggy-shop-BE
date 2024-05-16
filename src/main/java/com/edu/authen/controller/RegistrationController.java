package com.edu.authen.controller;

import com.edu.authen.DTO.RegistrationRequest;
import com.edu.authen.DTO.AuthenticationRequest;
import com.edu.authen.DTO.AuthenticationResponse;
import com.edu.authen.model.ConfirmationToken;
import com.edu.authen.service.RegistrationService;
import com.edu.authen.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class RegistrationController {

    private RegistrationService service;

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
            ,BindingResult result){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errs);
        }
    return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
   public ResponseEntity<?> register(
            @RequestBody @Valid AuthenticationRequest request ,
            BindingResult result
            ){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errs);
        }
        AuthenticationResponse res;
          res = service.authenticate(request);
           return ResponseEntity.ok(res);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmationRegister(@RequestParam("token")ConfirmationToken token){
        service.confirmRegistration(token);
        return ResponseEntity.ok("Confirmed");
    }

    @GetMapping("test")
    ResponseEntity<?> get1(){
        return ResponseEntity.ok(userService.findAll());
    }
}

package com.edu.authen.service;

import com.edu.authen.model.User;
import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    private Key getSigninKey(){
        String originalInput = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
        byte[] decodedBytes = Base64.getDecoder().decode(originalInput);
        return Keys.hmacShaKeyFor(decodedBytes);
    }

    // tạo ra jwt token từ thông tin user đã được xác thực
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, Long.toString( userDetails.getId()));
    }

    // tạo ra jwt token từ thông tin user đã được xác thực và các claims được định nghĩa sẵn
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    // kiểm tra xem jwt token có hợp lệ không
    public boolean validateToken(String authToken,User userDetail) {
      final Long userId = getIdUser(authToken);
      return (userId == userDetail.getId())&& !isTokenExpired(authToken);
    }

    // lấy id người dùng từ jwt token
    public Long getIdUser(String token) throws SignatureException {
        return Long.parseLong( extractClaim(token,Claims::getSubject));
    }

    private <T> T extractClaim(String token , Function<Claims , T> claimsResolvers){
        final Claims claims = extractAllClams(token);
        return claimsResolvers.apply(claims);
    }
    private Claims extractAllClams(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    // kiểm tra xem jwt token đã hết hạn chưa
    private Boolean isTokenExpired(String token) {

        return extractClaim(token,Claims::getExpiration).before(new Date());
    }


}

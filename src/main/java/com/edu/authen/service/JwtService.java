package com.edu.authen.service;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

//    private static final SecureRandom random = new SecureRandom();
//    SecretKey key = new SecretKeySpec(random.generateSeed(512), "ngadeptrai");
//    private static final String secret = key.toString();
private static final     SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // tạo ra jwt token từ thông tin user đã được xác thực
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // tạo ra jwt token từ thông tin user đã được xác thực và các claims được định nghĩa sẵn
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key)
                .compact();
    }

    // kiểm tra xem jwt token có hợp lệ không
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token" );
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token" );
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token" );
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    // lấy tên người dùng từ jwt token
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }



    // kiểm tra xem jwt token đã hết hạn chưa
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // lấy thời điểm hết hạn của jwt token
    public Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getExpiration();
    }
}

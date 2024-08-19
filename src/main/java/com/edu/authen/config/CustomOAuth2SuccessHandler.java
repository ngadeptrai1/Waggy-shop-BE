package com.edu.authen.config;

import com.edu.authen.model.CustomUserDetail;
import com.edu.authen.model.User;
import com.edu.authen.repository.UserRepository;
import com.edu.authen.service.JwtService;
import com.edu.authen.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
            ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();


        String provider = oauthToken.getAuthorizedClientRegistrationId();
        String providerId = oAuth2User.getName();
        // Generate JWT token
       Optional<User> user = userRepository.findByProviderId(providerId);
       if (user.isEmpty()) {
           User newUser = new User();
           newUser.setProviderId(providerId);
           newUser = userRepository.save(newUser);
           newUser.setBlocked(false);
           newUser.setEnable(true);
           CustomUserDetail customUserDetail = new CustomUserDetail(newUser);
           // Save JWT in a secure HttpOnly cookie
           Cookie jwtCookie = new Cookie("123",  jwtService.generateToken(customUserDetail));
           jwtCookie.setHttpOnly(true);
           jwtCookie.setSecure(true);
           jwtCookie.setPath("/");
           response.addCookie(jwtCookie);
       }
      else{
           CustomUserDetail customUserDetail = new CustomUserDetail(user.get());
           // Save JWT in a secure HttpOnly cookie
           Cookie jwtCookie = new Cookie("123",  jwtService.generateToken(customUserDetail));
//           jwtCookie.setHttpOnly(true);
//           jwtCookie.setSecure(true);
           jwtCookie.setPath("/");
           response.addCookie(jwtCookie);
       }
        // Redirect to frontend
        getRedirectStrategy().sendRedirect(request, response, "https://waggy-petshop.netlify.app/");
    }
}

package com.edu.authen.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig  {

    private final  JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // CSRF configuration
            .csrf(csrf -> csrf.disable()) // Disable CSRF if not needed

            .authorizeRequests(authorize -> authorize
                    .requestMatchers("/api/v1/auth/**", "api/v1/categories/**","/api/v1/brands"
                            , "api/v1/products/**","/api/v1/products", "/uploads","api/v1/users/like")
                            .permitAll() // Allow public access
                    .anyRequest().authenticated()
                    // Require authentication for other requests
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless configuration
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("*"));
                corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE","OPTIONS"));
                corsConfiguration.setAllowedHeaders(Arrays.asList("authorization","content-type","x-auth-token"));
                corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**",corsConfiguration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
    return http.build();
}
}

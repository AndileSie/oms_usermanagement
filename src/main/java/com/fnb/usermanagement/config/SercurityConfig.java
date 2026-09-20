package com.fnb.usermanagement.config;

import java.beans.BeanProperty;

import org.springframework.beans.factory.BeanRegistrarDslMarker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fnb.usermanagement.sercurity.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration 
@RequiredArgsConstructor 

public class SercurityConfig {

    private  final UserDetailsService userDetailsService;

    private final JwtAuthFilter jAuthFilter;



    @Bean 
    public  AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean 
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // T2.2: Public endpoints — permitAll
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register"
                        ).permitAll()
                        // Auth endpoints that require a valid JWT
                        .requestMatchers("/api/auth/refresh", "/api/auth/logout").authenticated()
                        // Customer endpoints
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(java.util.List.of("*"));
                configuration.setAllowedMethods(java.util.List.of("POST", "OPTIONS"));
                configuration.setAllowedHeaders(java.util.List.of("*"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
    @Bean
    public  PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

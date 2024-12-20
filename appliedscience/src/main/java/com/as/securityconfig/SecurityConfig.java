package com.as.securityconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.as.userloder.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF
            .cors(cors -> cors.disable()) // Disable CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/login", "/user/add").permitAll() // Allow login page access without authentication
                .requestMatchers("/user/**").permitAll() // Allow user endpoints
                .anyRequest().authenticated()) // Require authentication for all other requests
            .formLogin(form -> form
                .loginPage("/login").loginProcessingUrl("/login") // Custom login page
                .permitAll() // Allow everyone to access the login page
                .defaultSuccessUrl("/admin", true).permitAll() // Redirect after successful login
                .failureUrl("/login?error=true")) // Redirect on login failure
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless session management

        return http.build();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(); // Ensure this is your implementation of UserDetailsService
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService()); // Set UserDetailsService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Set PasswordEncoder
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean coresFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
        corsConfiguration.addAllowedOriginPattern("*"); // Allow all origins (adjust for your needs)
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L); // Cache the preflight response for 1 hour (3600 seconds)

        // Register the CORS configuration with the path pattern "/**"
        source.registerCorsConfiguration("/**", corsConfiguration);

        // Create a CorsFilter using the source and register it
        FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(0); // Ensure this filter runs first
        return bean;
    }
}

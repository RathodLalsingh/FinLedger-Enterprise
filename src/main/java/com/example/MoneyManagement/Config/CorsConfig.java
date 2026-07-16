package com.example.MoneyManagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    //Registers global CORS rules for all REST APIs.
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration  configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        configuration.setAllowedMethods(
                Arrays.asList("GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"));
        configuration.setAllowedMethods(List.of("*"));

        configuration.setExposedHeaders(List.of("Authorization"));
        //allows credentials
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        //Register cors configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;

    }
}

package com.ggomezr.bookingsystem.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
    Configuring Cross-Origin Resource Sharing (CORS)
    - Defines rules that allow or restrict resource requests from a domain other than the server's
    - Controls how browsers allow a web page to access resources on a different domain.
*/

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("https://ggomez1403.github.io/");
        configuration.addAllowedOrigin("https://hotel-booking-system-railway-production.up.railway.app/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}

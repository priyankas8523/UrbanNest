package com.UrbanNest.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    
//    @Value("${allowed-endpoints}")
//    private List<String> allowedEndpoints;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                               // .requestMatchers(allowedEndpoints.toArray(new String[0])).permitAll()
                
                      //  .requestMatchers("/api/master/person","/api/unit").access((authentication, object) -> )
                        .requestMatchers("/api/master/login").permitAll()
                        
                        //.requestMatchers("/api/unit").permitAll()
                        .anyRequest().authenticated()

//                                .requestMatchers("/api/**").permitAll()
//                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        System.out.println("sc----------------------------------------------------------------------------------------------------------------------------");
        return http.build();
    }
}

package com.laioffer.staybooking;

import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public Storage storage() throws IOException {
        Credentials credentials = ServiceAccountCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("credentials.json"));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    @Bean
    public GeoApiContext geoApiContext(@Value("${staybooking.geocoding.key}") String apiKey) {
        return new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

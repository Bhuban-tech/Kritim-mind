package KritimBackend.KritimBackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/", "/create", "/login", "/logout",
                                "/api/notice/all", "/api/notice/{id}",
                                "/api/v1/company/all", "/api/v1/company/{id}",
                                "/api/notice/add", "/api/notice/update/**", "/api/notice/delete/**",
                                "/api/v1/company/add", "/api/v1/company/update/**", "/api/v1/company/delete/**",
                                "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()
                        // Admin can POST, PUT, DELETE notices and companies
                        .requestMatchers(HttpMethod.POST, "/api/notice/**", "/api/v1/company/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/notice/**", "/api/v1/company/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/notice/**", "/api/v1/company/**").hasRole("Admin")
                        // Developer and Admin can GET
                        .requestMatchers(HttpMethod.GET, "/api/notice/**", "/api/v1/company/**").hasAnyRole("Admin", "Developer")
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // your frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

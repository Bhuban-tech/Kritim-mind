package KritimBackend.KritimBackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
=======
>>>>>>> hiyang
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
<<<<<<< HEAD
                .authorizeHttpRequests(auth -> auth
                        // Allow Swagger URLs without authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Permit all other requests (you can restrict later)
                        .anyRequest().permitAll()
                )
=======
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
>>>>>>> hiyang
                .httpBasic(basic -> basic.disable())
                .formLogin(login -> login.disable())
                .logout(logout -> logout.disable());
        return http.build();
    }
<<<<<<< HEAD
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
};
=======
}

>>>>>>> hiyang

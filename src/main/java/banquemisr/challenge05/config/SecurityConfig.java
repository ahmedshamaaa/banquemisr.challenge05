package banquemisr.challenge05.config;

import banquemisr.challenge05.security.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Public endpoints
                        .requestMatchers(HttpMethod.GET, "/api/tasks/**").authenticated() // Allow GET for authenticated users
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("ADMIN") // DELETE for ADMIN only
                        .requestMatchers(HttpMethod.POST, "/api/tasks/**").hasAnyRole("USER", "ADMIN") // POST for USER & ADMIN
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasAnyRole("USER", "ADMIN") // PUT for USER & ADMIN
                )
                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter();
    }
}

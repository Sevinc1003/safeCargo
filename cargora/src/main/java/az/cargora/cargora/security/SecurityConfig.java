package az.cargora.cargora.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enables @PreAuthorize annotations
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager jdbcDao = new JdbcUserDetailsManager(dataSource);

        jdbcDao.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM accounts WHERE username = ?");

        // asagidaki query'ni athorities hazir olanda uygunlasdiraram
        // jdbcDao.setAuthoritiesByUsernameQuery(
        // "SELECT username, role FROM users WHERE username = ?"
        // );

        return jdbcDao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for REST API testing
                .csrf(csrf -> csrf.disable())

                // Enable HTTP Basic authentication for now
                .httpBasic(Customizer.withDefaults())

                // Configure endpoint access rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers("/api/auth/**").permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // All other endpoints require authentication
                        .anyRequest().authenticated());

        return http.build();
    }
}

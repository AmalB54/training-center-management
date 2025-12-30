package com.iit.trainingcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@EnableMethodSecurity

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // =====================
                // H2 Console
                // =====================
                .requestMatchers("/h2-console/**").permitAll()

                // =====================
                // Static resources
                // =====================
                .requestMatchers("/css/**", "/js/**").permitAll()

                // =====================
                // ADMIN (Thymeleaf)
                // =====================
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // =====================
                // STUDENTS / TRAINERS (ADMIN only)
                // =====================
                .requestMatchers("/api/students/**").hasRole("ADMIN")
                .requestMatchers("/api/trainers/**").hasRole("ADMIN")

                // =====================
                // COURSES
                // =====================
                // Consultation
                .requestMatchers(HttpMethod.GET, "/api/courses/**")
                    .hasAnyRole("ADMIN", "STUDENT", "TRAINER")

                // Gestion
                .requestMatchers(HttpMethod.POST, "/api/courses/**")
                    .hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.PUT, "/api/courses/**")
                    .hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/**")
                    .hasAnyRole("ADMIN", "TRAINER")

                // =====================
                // ENROLLMENTS
                // =====================
                // Consultation personnelle
                .requestMatchers(HttpMethod.GET, "/api/enrollments/student/**")
                    .hasAnyRole("ADMIN", "STUDENT")

                // Création inscription
                .requestMatchers(HttpMethod.POST, "/api/enrollments/**")
                    .hasAnyRole("ADMIN", "STUDENT")

                // Suppression / modification
                .requestMatchers(HttpMethod.PUT, "/api/enrollments/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/enrollments/**")
                    .hasRole("ADMIN")

                // =====================
                // GRADES
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/grades/**")
                    .hasAnyRole("ADMIN", "STUDENT", "TRAINER")

                .requestMatchers(HttpMethod.POST, "/api/grades/**")
                    .hasAnyRole("ADMIN", "TRAINER")

                .anyRequest().authenticated()
            )

            // Authentification
            .formLogin(form -> form
                    .defaultSuccessUrl("/admin", true)
                    .permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .logout(Customizer.withDefaults())

            // H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // =====================s
    // Password Encoder
    // =====================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =====================
    // InMemory Users
    // =====================
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails student = User.builder()
                .username("student")
                .password(encoder.encode("student123"))
                .roles("STUDENT")
                .build();

        UserDetails trainer = User.builder()
                .username("trainer")
                .password(encoder.encode("trainer123"))
                .roles("TRAINER")
                .build();

        return new InMemoryUserDetailsManager(admin, student, trainer);
    }
}

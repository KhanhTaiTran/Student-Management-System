package com.example.studentmanagementsystem.security;

import com.example.studentmanagementsystem.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    // hash password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // configure authenticationmanage to handle login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // connect UserDetailsService with AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/img/**").permitAll()

                        // role based access control
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // other enpoints
                        .requestMatchers(HttpMethod.GET, "/api/user/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")

                        // All other requests need authentication
                        .anyRequest().authenticated())
                // configure stateless (don't save session on server)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // add JWT Filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package com.github.csifinos.wsendpoint.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({UserProperties.class})
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/api/**", "/ws/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(false)
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            // in case we destroy ws session here
                        })
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserProperties userProperties) {
        var users = userProperties.getUsers()
                .stream()
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(passwordEncoder().encode(user.getPassword()))
                        .roles("USER")
                        .build())
                .toList();

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

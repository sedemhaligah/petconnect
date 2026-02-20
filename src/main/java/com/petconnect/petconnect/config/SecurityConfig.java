package com.petconnect.petconnect.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // Completely bypass security for static assets
    return (web) -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .cors(withDefaults())
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/logout", "/api/availability/add", "/ws-chat/**")
            )
            .authorizeHttpRequests(auth -> auth
                    // Public pages
                    .requestMatchers(
                            "/", "/homepage",
                            "/login", "/register",
                            "/forgot-password", "/reset-password/**",
                            "/ws-chat/**"
                    ).permitAll()

                    // Everything else needs auth
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    // don’t force "/" always; go back to originally requested URL if there is one
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            )
            .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

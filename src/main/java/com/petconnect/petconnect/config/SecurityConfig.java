package com.petconnect.petconnect.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.cors(withDefaults())
            .csrf(
                    csrf -> csrf.ignoringRequestMatchers("/logout", "/api/availability/add", "/ws-chat/**"))
            .authorizeHttpRequests(
                    auth ->
                            auth.requestMatchers(
                                            "/",
                                            "/homepage",
                                            "/Profilseite",
                                            "/register",
                                            "/login",
                                            "/logout",
                                            "/profile",
                                            "/images/**",
                                            "/anmeldens.css",
                                            "/login.css",
                                            "/forgot-password",
                                            "/reset-password/**",
                                            "forgotstyle.css",
                                            "index.css",
                                            "Profilseite.css",
                                            "/Profilbearbeitung.css",
                                            "/betreuerfinden.css",
                                            "Profilbearbeitung",
                                            "/chat",
                                            "chat-overview",
                                            "chat.css",
                                            "chats.css",
                                            "Contact.css",
                                            "/uploads/**",
                                            "/ws-chat/**" //
                                    )
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated())
            .formLogin(
                    form ->
                            form.loginPage("/login")
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/", true)
                                    .permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/login"))
            .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("http://localhost:8003"); // For production, be specific
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

package com.leonam.gerenciador_eventos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.leonam.gerenciador_eventos.security.JwtAuthenticationEntryPoint;
import com.leonam.gerenciador_eventos.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        public SecurityConfig(
                        JwtAuthenticationFilter jwtAuthenticationFilter,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {

                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())

                                .formLogin(form -> form.disable())

                                .httpBasic(basic -> basic.disable())

                                .authorizeHttpRequests(auth -> auth

                                                // =========================
                                                // ROTAS PÚBLICAS
                                                // =========================

                                                .requestMatchers(
                                                                "/login",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // Qualquer pessoa pode visualizar eventos
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/eventos",
                                                                "/eventos/{id}")
                                                .permitAll()

                                                // =========================
                                                // ROTAS PROTEGIDAS
                                                // =========================

                                                // Administradores
                                                .requestMatchers(
                                                                "/administradores/**")
                                                .authenticated()

                                                // Operações administrativas de eventos
                                                .requestMatchers(
                                                                "/eventos/**")
                                                .authenticated()

                                                // Qualquer outra rota
                                                .anyRequest()
                                                .authenticated())

                                // Resposta personalizada para acesso sem autenticação
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint(
                                                                jwtAuthenticationEntryPoint))

                                // Filtro JWT
                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
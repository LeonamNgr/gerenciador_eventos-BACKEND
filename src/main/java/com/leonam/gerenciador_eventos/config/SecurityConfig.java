package com.leonam.gerenciador_eventos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.leonam.gerenciador_eventos.security.JwtAccessDeniedHandler;
import com.leonam.gerenciador_eventos.security.JwtAuthenticationEntryPoint;
import com.leonam.gerenciador_eventos.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        public SecurityConfig(
                        JwtAuthenticationFilter jwtAuthenticationFilter,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                        JwtAccessDeniedHandler jwtAccessDeniedHandler) {

                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
                this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
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

                                .cors(cors -> {
                                })

                                .formLogin(form -> form.disable())

                                .httpBasic(basic -> basic.disable())

                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint(
                                                                jwtAuthenticationEntryPoint)
                                                .accessDeniedHandler(
                                                                jwtAccessDeniedHandler))

                                .authorizeHttpRequests(auth -> auth

                                                // Login e documentação públicos
                                                .requestMatchers(
                                                                "/login",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // Cadastro de administrador público
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/administradores")
                                                .permitAll()

                                                // Solicitação de alteração de senha pública
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/solicitacoes-senha")
                                                .permitAll()

                                                // Consulta pública de eventos
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/eventos",
                                                                "/eventos/*")
                                                .permitAll()

                                                // Área administrativa protegida
                                                .requestMatchers(
                                                                "/administradores/**",
                                                                "/eventos/**")
                                                .authenticated()

                                                // Qualquer outro endpoint também exige autenticação
                                                .anyRequest()
                                                .authenticated())

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
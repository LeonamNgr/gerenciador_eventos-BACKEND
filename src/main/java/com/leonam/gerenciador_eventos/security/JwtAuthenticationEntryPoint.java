package com.leonam.gerenciador_eventos.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.json.JsonMapper;

@Component
public class JwtAuthenticationEntryPoint
                implements AuthenticationEntryPoint {

        private final JsonMapper jsonMapper;

        public JwtAuthenticationEntryPoint(JsonMapper jsonMapper) {
                this.jsonMapper = jsonMapper;
        }

        @Override
        public void commence(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException)
                        throws IOException, ServletException {

                ErroResponseDTO erro = new ErroResponseDTO(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                "Não autenticado",
                                "Autenticação necessária. Informe um token JWT válido.",
                                LocalDateTime.now());

                response.setStatus(
                                HttpServletResponse.SC_UNAUTHORIZED);

                response.setContentType(
                                MediaType.APPLICATION_JSON_VALUE);

                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(
                                jsonMapper.writeValueAsString(erro));
        }
}
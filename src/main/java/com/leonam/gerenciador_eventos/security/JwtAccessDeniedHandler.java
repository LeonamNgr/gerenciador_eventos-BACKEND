package com.leonam.gerenciador_eventos.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.json.JsonMapper;

@Component
public class JwtAccessDeniedHandler
        implements AccessDeniedHandler {

    private final JsonMapper jsonMapper;

    public JwtAccessDeniedHandler(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ErroResponseDTO erro = new ErroResponseDTO(
                HttpServletResponse.SC_FORBIDDEN,
                "Acesso negado",
                "Você não possui permissão para acessar este recurso.",
                LocalDateTime.now());

        response.setStatus(
                HttpServletResponse.SC_FORBIDDEN);

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE);

        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                jsonMapper.writeValueAsString(erro));
    }
}
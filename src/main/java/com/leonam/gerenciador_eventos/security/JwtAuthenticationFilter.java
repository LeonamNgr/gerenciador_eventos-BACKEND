package com.leonam.gerenciador_eventos.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leonam.gerenciador_eventos.repository.AdministradorRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AdministradorRepository administradorRepository;

    public JwtAuthenticationFilter(
            TokenService tokenService,
            AdministradorRepository administradorRepository) {

        this.tokenService = tokenService;
        this.administradorRepository = administradorRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);

        if (token != null) {

            String email = tokenService.validarToken(token);

            if (email != null) {

                administradorRepository
                        .findByEmail(email)
                        .ifPresent(administrador -> {

                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    administrador,
                                    null,
                                    Collections.emptyList());

                            SecurityContextHolder
                                    .getContext()
                                    .setAuthentication(authentication);
                        });
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null
                || authorizationHeader.isBlank()) {

            return null;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {

            return null;
        }

        String token = authorizationHeader.substring(7).trim();

        if (token.isBlank()) {

            return null;
        }

        return token;
    }
}
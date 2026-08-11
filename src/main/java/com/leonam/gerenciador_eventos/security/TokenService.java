package com.leonam.gerenciador_eventos.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.entity.Administrador;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private static final long DURACAO_TOKEN_MILLIS = 2 * 60 * 60 * 1000L;

    private final SecretKey secretKey;

    public TokenService(
            @Value("${api.security.token.secret}") String secret) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Administrador administrador) {

        Date agora = new Date();

        Date expiracao = new Date(
                agora.getTime() + DURACAO_TOKEN_MILLIS);

        return Jwts.builder()
                .subject(administrador.getEmail())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(secretKey)
                .compact();
    }

    public String validarToken(String token) {

        try {

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (Exception exception) {

            logger.debug(
                    "Token JWT inválido ou expirado: {}",
                    exception.getMessage());

            return null;
        }
    }
}
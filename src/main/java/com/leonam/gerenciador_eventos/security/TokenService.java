package com.leonam.gerenciador_eventos.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.entity.Administrador;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private final SecretKey secretKey;

    public TokenService(
            @Value("${api.security.token.secret}") String secret) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Administrador administrador) {

        Date agora = new Date();

        Date expiracao = new Date(
                agora.getTime() + 2 * 60 * 60 * 1000);

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

        } catch (Exception e) {

            return null;
        }
    }
}
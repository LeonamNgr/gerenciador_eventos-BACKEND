package com.leonam.gerenciador_eventos.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.dto.request.LoginRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.dto.response.LoginResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.exception.CredenciaisInvalidasException;
import com.leonam.gerenciador_eventos.repository.AdministradorRepository;
import com.leonam.gerenciador_eventos.security.TokenService;

@Service
public class AuthService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {

        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Administrador administrador = administradorRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException(
                        "E-mail ou senha inválidos."));

        if (!passwordEncoder.matches(
                dto.getSenha(),
                administrador.getSenha())) {

            throw new CredenciaisInvalidasException(
                    "E-mail ou senha inválidos.");
        }

        String token = tokenService.gerarToken(administrador);

        AdministradorResponseDTO administradorResponse = new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail());

        return new LoginResponseDTO(
                token,
                administradorResponse);
    }
}
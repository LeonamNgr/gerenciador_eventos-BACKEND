package com.leonam.gerenciador_eventos.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.repository.AdministradorRepository;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorService(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {

        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdministradorResponseDTO cadastrar(AdministradorRequestDTO dto) {

        if (administradorRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Administrador administrador = new Administrador(
                dto.getNome(),
                dto.getEmail(),
                senhaCriptografada);

        administrador = administradorRepository.save(administrador);

        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail());
    }
}
package com.leonam.gerenciador_eventos.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.exception.AdministradorNaoEncontradoException;
import com.leonam.gerenciador_eventos.exception.EmailJaCadastradoException;
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

    public AdministradorResponseDTO cadastrar(
            AdministradorRequestDTO dto) {

        if (administradorRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException(
                    "E-mail já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Administrador administrador = new Administrador(
                dto.getNome(),
                dto.getEmail(),
                senhaCriptografada);

        administrador = administradorRepository.save(administrador);

        return converterParaResponse(administrador);
    }

    public AdministradorResponseDTO buscarPorId(Long id) {

        Administrador administrador = buscarEntidadePorId(id);

        return converterParaResponse(administrador);
    }

    public Administrador buscarEntidadePorId(Long id) {

        return administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNaoEncontradoException(
                        "Administrador não encontrado."));
    }

    public List<AdministradorResponseDTO> buscarPorNome(
            String nome) {

        return administradorRepository
                .findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public List<AdministradorResponseDTO> buscarTodos() {

        return administradorRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public AdministradorResponseDTO editar(
            Long id,
            AdministradorRequestDTO dto) {

        Administrador administrador = buscarEntidadePorId(id);

        if (!administrador.getEmail().equals(dto.getEmail())
                && administradorRepository.existsByEmail(dto.getEmail())) {

            throw new EmailJaCadastradoException(
                    "E-mail já cadastrado.");
        }

        administrador.setNome(dto.getNome());
        administrador.setEmail(dto.getEmail());

        if (dto.getSenha() != null
                && !dto.getSenha().isBlank()) {

            administrador.setSenha(
                    passwordEncoder.encode(dto.getSenha()));
        }

        administrador = administradorRepository.save(administrador);

        return converterParaResponse(administrador);
    }

    public void deletar(Long id) {

        Administrador administrador = buscarEntidadePorId(id);

        administradorRepository.delete(administrador);
    }

    private AdministradorResponseDTO converterParaResponse(
            Administrador administrador) {

        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail());
    }
}
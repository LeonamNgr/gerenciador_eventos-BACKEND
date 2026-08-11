package com.leonam.gerenciador_eventos.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.exception.AdministradorNaoEncontradoException;
import com.leonam.gerenciador_eventos.exception.EmailJaCadastradoException;
import com.leonam.gerenciador_eventos.exception.EventosVinculadosException;
import com.leonam.gerenciador_eventos.repository.AdministradorRepository;
import com.leonam.gerenciador_eventos.repository.EventoRepository;

@Service
public class AdministradorService {

        private final AdministradorRepository administradorRepository;
        private final EventoRepository eventoRepository;
        private final PasswordEncoder passwordEncoder;

        public AdministradorService(
                        AdministradorRepository administradorRepository,
                        EventoRepository eventoRepository,
                        PasswordEncoder passwordEncoder) {

                this.administradorRepository = administradorRepository;
                this.eventoRepository = eventoRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public AdministradorResponseDTO cadastrar(
                        AdministradorRequestDTO dto) {

                if (administradorRepository.existsByEmail(dto.getEmail())) {

                        throw new EmailJaCadastradoException(
                                        "O e-mail '" + dto.getEmail()
                                                        + "' já está cadastrado.");
                }

                String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

                Administrador administrador = new Administrador(
                                dto.getNome(),
                                dto.getEmail(),
                                senhaCriptografada);

                administrador = administradorRepository.save(administrador);

                return converterParaResponse(administrador);
        }

        @Transactional(readOnly = true)
        public AdministradorResponseDTO buscarPorId(Long id) {

                Administrador administrador = buscarEntidadePorId(id);

                return converterParaResponse(administrador);
        }

        @Transactional(readOnly = true)
        public Administrador buscarEntidadePorId(Long id) {

                return administradorRepository.findById(id)
                                .orElseThrow(() -> new AdministradorNaoEncontradoException(
                                                "Administrador com ID "
                                                                + id
                                                                + " não encontrado."));
        }

        @Transactional(readOnly = true)
        public List<AdministradorResponseDTO> buscarPorNome(
                        String nome) {

                return administradorRepository
                                .findByNomeContainingIgnoreCase(nome)
                                .stream()
                                .map(this::converterParaResponse)
                                .toList();
        }

        @Transactional(readOnly = true)
        public List<AdministradorResponseDTO> buscarTodos() {

                return administradorRepository.findAll()
                                .stream()
                                .map(this::converterParaResponse)
                                .toList();
        }

        @Transactional
        public AdministradorResponseDTO editar(
                        Long id,
                        AdministradorRequestDTO dto) {

                Administrador administrador = buscarEntidadePorId(id);

                if (!administrador.getEmail().equals(dto.getEmail())
                                && administradorRepository
                                                .existsByEmail(dto.getEmail())) {

                        throw new EmailJaCadastradoException(
                                        "O e-mail '" + dto.getEmail()
                                                        + "' já está cadastrado.");
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

        @Transactional
        public void deletar(Long id) {

                Administrador administrador = buscarEntidadePorId(id);

                if (eventoRepository.existsByAdministradorId(id)) {

                        throw new EventosVinculadosException(
                                        "Não é possível excluir o administrador com ID "
                                                        + id
                                                        + " porque existem eventos vinculados a ele.");
                }

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
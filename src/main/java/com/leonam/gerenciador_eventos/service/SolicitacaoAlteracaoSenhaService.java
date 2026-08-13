package com.leonam.gerenciador_eventos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonam.gerenciador_eventos.dto.request.AtenderSolicitacaoSenhaRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.SolicitacaoAlteracaoSenhaResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.entity.SolicitacaoAlteracaoSenha;
import com.leonam.gerenciador_eventos.enums.StatusSolicitacaoSenha;
import com.leonam.gerenciador_eventos.exception.AdministradorNaoEncontradoException;
import com.leonam.gerenciador_eventos.exception.CredenciaisInvalidasException;
import com.leonam.gerenciador_eventos.repository.AdministradorRepository;
import com.leonam.gerenciador_eventos.repository.SolicitacaoAlteracaoSenhaRepository;

@Service
public class SolicitacaoAlteracaoSenhaService {

    private final SolicitacaoAlteracaoSenhaRepository solicitacaoRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public SolicitacaoAlteracaoSenhaService(
            SolicitacaoAlteracaoSenhaRepository solicitacaoRepository,
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {

        this.solicitacaoRepository = solicitacaoRepository;
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void solicitar(String email) {

        Administrador administrador = administradorRepository.findByEmail(email)
                .orElseThrow(() -> new AdministradorNaoEncontradoException(
                        "Administrador não encontrado."));

        boolean possuiSolicitacaoPendente = solicitacaoRepository
                .existsByAdministradorIdAndStatus(
                        administrador.getId(),
                        StatusSolicitacaoSenha.PENDENTE);

        if (possuiSolicitacaoPendente) {
            return;
        }

        SolicitacaoAlteracaoSenha solicitacao = new SolicitacaoAlteracaoSenha(
                administrador);

        solicitacaoRepository.save(solicitacao);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoAlteracaoSenhaResponseDTO> listarPendentes() {

        return solicitacaoRepository
                .findByStatus(StatusSolicitacaoSenha.PENDENTE)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional
    public void atender(
            Long id,
            AtenderSolicitacaoSenhaRequestDTO dto) {

        SolicitacaoAlteracaoSenha solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Solicitação de alteração de senha não encontrada."));

        if (solicitacao.getStatus() != StatusSolicitacaoSenha.PENDENTE) {

            throw new IllegalArgumentException(
                    "Esta solicitação já foi atendida ou cancelada.");
        }

        if (!dto.getNovaSenha().equals(
                dto.getConfirmarNovaSenha())) {

            throw new CredenciaisInvalidasException(
                    "A nova senha e a confirmação da senha não conferem.");
        }

        Administrador administradorAtendente = obterAdministradorLogado();

        Administrador administrador = solicitacao.getAdministrador();

        administrador.setSenha(
                passwordEncoder.encode(
                        dto.getNovaSenha()));

        administradorRepository.save(
                administrador);

        solicitacao.setStatus(
                StatusSolicitacaoSenha.ATENDIDA);

        solicitacao.setDataAtendimento(
                LocalDateTime.now());

        solicitacao.setAdministradorAtendente(
                administradorAtendente);

        solicitacaoRepository.save(
                solicitacao);
    }

    private SolicitacaoAlteracaoSenhaResponseDTO converterParaResponse(
            SolicitacaoAlteracaoSenha solicitacao) {

        Administrador administrador = solicitacao.getAdministrador();

        Administrador administradorAtendente = solicitacao.getAdministradorAtendente();

        return new SolicitacaoAlteracaoSenhaResponseDTO(
                solicitacao.getId(),
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail(),
                solicitacao.getStatus(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getDataAtendimento(),
                administradorAtendente != null
                        ? administradorAtendente.getId()
                        : null,
                administradorAtendente != null
                        ? administradorAtendente.getNome()
                        : null);
    }

    private Administrador obterAdministradorLogado() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof Administrador)) {

            throw new AdministradorNaoEncontradoException(
                    "Administrador autenticado não encontrado.");
        }

        return (Administrador) authentication.getPrincipal();
    }
}
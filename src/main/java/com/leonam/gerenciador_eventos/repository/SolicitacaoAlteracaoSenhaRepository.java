package com.leonam.gerenciador_eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonam.gerenciador_eventos.entity.SolicitacaoAlteracaoSenha;
import com.leonam.gerenciador_eventos.enums.StatusSolicitacaoSenha;

public interface SolicitacaoAlteracaoSenhaRepository
        extends JpaRepository<SolicitacaoAlteracaoSenha, Long> {

    List<SolicitacaoAlteracaoSenha> findByStatus(
            StatusSolicitacaoSenha status);

    boolean existsByAdministradorIdAndStatus(
            Long administradorId,
            StatusSolicitacaoSenha status);
}
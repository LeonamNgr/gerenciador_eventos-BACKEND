package com.leonam.gerenciador_eventos.entity;

import java.time.LocalDateTime;

import com.leonam.gerenciador_eventos.enums.StatusSolicitacaoSenha;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "solicitacao_alteracao_senha")
public class SolicitacaoAlteracaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusSolicitacaoSenha status;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_atendimento")
    private LocalDateTime dataAtendimento;

    @ManyToOne
    @JoinColumn(name = "administrador_atendente_id")
    private Administrador administradorAtendente;

    public SolicitacaoAlteracaoSenha(
            Administrador administrador) {

        this.administrador = administrador;
        this.status = StatusSolicitacaoSenha.PENDENTE;
        this.dataSolicitacao = LocalDateTime.now();
    }
}
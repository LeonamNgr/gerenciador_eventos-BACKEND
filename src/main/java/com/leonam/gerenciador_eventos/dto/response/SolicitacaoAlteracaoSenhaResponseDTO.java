package com.leonam.gerenciador_eventos.dto.response;

import java.time.LocalDateTime;

import com.leonam.gerenciador_eventos.enums.StatusSolicitacaoSenha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Dados de uma solicitação de alteração de senha")
public class SolicitacaoAlteracaoSenhaResponseDTO {

    @Schema(description = "ID da solicitação", example = "1")
    private Long id;

    @Schema(description = "ID do administrador que solicitou a alteração", example = "5")
    private Long administradorId;

    @Schema(description = "Nome do administrador", example = "João Silva")
    private String administradorNome;

    @Schema(description = "E-mail do administrador", example = "joao@email.com")
    private String administradorEmail;

    @Schema(description = "Status da solicitação", example = "PENDENTE")
    private StatusSolicitacaoSenha status;

    @Schema(description = "Data e hora da solicitação")
    private LocalDateTime dataSolicitacao;

    @Schema(description = "Data e hora do atendimento")
    private LocalDateTime dataAtendimento;

    @Schema(description = "ID do administrador que atendeu a solicitação", example = "1")
    private Long administradorAtendenteId;

    @Schema(description = "Nome do administrador que atendeu a solicitação", example = "Administrador Principal")
    private String administradorAtendenteNome;
}
package com.leonam.gerenciador_eventos.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Resposta padrão para erros da API")
public class ErroResponseDTO {

    @Schema(description = "Código HTTP do erro", example = "404")
    private int status;

    @Schema(description = "Mensagem explicando o motivo do erro", example = "Administrador não encontrado.")
    private String mensagem;

    @Schema(description = "Data e hora em que o erro ocorreu", example = "2026-08-11T18:30:00")
    private LocalDateTime dataHora;
}
package com.leonam.gerenciador_eventos.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta padrão de erro da API")
public class ErroResponseDTO {

    @Schema(description = "Código HTTP do erro", example = "404")
    private int status;

    @Schema(description = "Tipo do erro", example = "Evento não encontrado")
    private String erro;

    @Schema(description = "Mensagem detalhada do erro", example = "Evento não encontrado.")
    private String mensagem;

    @Schema(description = "Data e hora em que ocorreu o erro", example = "2026-08-11T18:05:44")
    private LocalDateTime dataHora;
}
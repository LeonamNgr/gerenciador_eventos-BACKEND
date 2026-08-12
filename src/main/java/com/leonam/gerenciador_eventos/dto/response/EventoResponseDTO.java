package com.leonam.gerenciador_eventos.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do evento")
public class EventoResponseDTO {

    @Schema(description = "ID do evento")
    private Long id;

    @Schema(description = "Nome do evento")
    private String nomeEvento;

    @Schema(description = "Data do evento", example = "2026-09-10")
    private LocalDate data;

    @Schema(description = "Hora do evento", example = "19:30:00")
    private LocalTime hora;

    @Schema(description = "Local do evento")
    private String local;

    @Schema(description = "Descrição do evento")
    private String descricao;

    @Schema(description = "URL ou caminho da imagem do evento")
    private String imagem;

    @Schema(description = "ID do administrador responsável")
    private Long administradorId;

    @Schema(description = "Nome do administrador responsável")
    private String administradorNome;
}
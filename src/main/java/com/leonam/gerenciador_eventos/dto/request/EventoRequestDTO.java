package com.leonam.gerenciador_eventos.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoRequestDTO {

    @Schema(description = "Nome do evento")
    @NotBlank(message = "O nome do evento é obrigatório.")
    @Size(max = 200, message = "O nome do evento deve ter no máximo 200 caracteres.")
    private String nomeEvento;

    @Schema(description = "Data do evento", example = "2026-09-10")
    @NotNull(message = "A data do evento é obrigatória.")
    private LocalDate data;

    @Schema(description = "Hora do evento", example = "19:30:00")
    @NotNull(message = "A hora do evento é obrigatória.")
    private LocalTime hora;

    @Schema(description = "Local do evento")
    @NotBlank(message = "O local do evento é obrigatório.")
    @Size(max = 200, message = "O local do evento deve ter no máximo 200 caracteres.")
    private String local;

    @Schema(description = "Descrição do evento")
    @NotBlank(message = "A descrição do evento é obrigatória.")
    @Size(max = 200, message = "A descrição do evento deve ter no máximo 200 caracteres.")
    private String descricao;

    @Schema(description = "URL ou caminho da imagem do evento")
    @Size(max = 500, message = "A imagem deve ter no máximo 500 caracteres.")
    private String imagem;

    @Schema(description = "ID do administrador responsável pelo evento")
    @NotNull(message = "O administrador é obrigatório.")
    private Long administradorId;
}
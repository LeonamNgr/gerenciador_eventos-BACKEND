package com.leonam.gerenciador_eventos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do administrador")
public class AdministradorResponseDTO {

    @Schema(description = "ID do administrador")
    private Long id;

    @Schema(description = "Nome do administrador")
    private String nome;

    @Schema(description = "E-mail do administrador")
    private String email;
}
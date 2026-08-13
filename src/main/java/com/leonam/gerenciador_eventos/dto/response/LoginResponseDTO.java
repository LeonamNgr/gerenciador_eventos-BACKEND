package com.leonam.gerenciador_eventos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Dados retornados após autenticação")
public class LoginResponseDTO {

    @Schema(description = "Token JWT de autenticação")
    private String token;

    @Schema(description = "Dados do administrador autenticado")
    private AdministradorResponseDTO administrador;
}
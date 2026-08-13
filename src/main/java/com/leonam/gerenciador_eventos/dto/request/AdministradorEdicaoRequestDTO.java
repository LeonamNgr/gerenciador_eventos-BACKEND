package com.leonam.gerenciador_eventos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para edição de administrador")
public class AdministradorEdicaoRequestDTO {

    @Schema(description = "Nome do administrador", example = "Maria")
    @NotBlank(message = "O nome do administrador é obrigatório.")
    @Size(max = 100, message = "O nome do administrador deve ter no máximo 100 caracteres.")
    private String nome;

    @Schema(description = "E-mail do administrador", example = "maria@email.com")
    @NotBlank(message = "O e-mail do administrador é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 100, message = "O e-mail do administrador deve ter no máximo 100 caracteres.")
    private String email;
}
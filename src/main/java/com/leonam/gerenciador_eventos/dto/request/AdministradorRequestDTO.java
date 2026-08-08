package com.leonam.gerenciador_eventos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministradorRequestDTO {

    @Schema(description = "Nome do administrador")
    @NotBlank(message = "O nome do administrador é obrigatório.")
    @Size(max = 100, message = "O nome do administrador deve ter no máximo 100 caracteres.")
    private String nome;

    @Schema(description = "E-mail do administrador")
    @NotBlank(message = "O e-mail do administrador é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 100, message = "O e-mail do administrador deve ter no máximo 100 caracteres.")
    private String email;

    @Schema(description = "Senha do administrador")
    @NotBlank(message = "A senha do administrador é obrigatória.")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
    private String senha;
}
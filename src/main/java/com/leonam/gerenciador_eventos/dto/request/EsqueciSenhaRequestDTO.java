package com.leonam.gerenciador_eventos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para solicitar alteração de senha")
public class EsqueciSenhaRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Schema(description = "E-mail do administrador que esqueceu a senha", example = "admin@email.com")
    private String email;
}
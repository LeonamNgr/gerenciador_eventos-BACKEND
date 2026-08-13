package com.leonam.gerenciador_eventos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para alteração da senha do administrador")
public class AlterarSenhaRequestDTO {

    @NotBlank(message = "A senha atual é obrigatória.")
    @Schema(description = "Senha atual do administrador", example = "SenhaAtual123")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 8, max = 20, message = "A nova senha deve possuir entre 8 e 20 caracteres.")
    @Schema(description = "Nova senha do administrador", example = "NovaSenha123")
    private String novaSenha;

    @NotBlank(message = "A confirmação da nova senha é obrigatória.")
    @Size(min = 8, max = 20, message = "A confirmação da nova senha deve possuir entre 8 e 20 caracteres.")
    @Schema(description = "Confirmação da nova senha", example = "NovaSenha123")
    private String confirmarNovaSenha;
}
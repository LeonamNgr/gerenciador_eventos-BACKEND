package com.leonam.gerenciador_eventos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para atendimento de uma solicitação de alteração de senha")
public class AtenderSolicitacaoSenhaRequestDTO {

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
    @Schema(description = "Nova senha definida para o administrador", example = "NovaSenha@123")
    private String novaSenha;

    @NotBlank(message = "A confirmação da nova senha é obrigatória.")
    @Size(min = 8, max = 20, message = "A confirmação da senha deve ter entre 8 e 20 caracteres.")
    @Schema(description = "Confirmação da nova senha", example = "NovaSenha@123")
    private String confirmarNovaSenha;
}
package com.leonam.gerenciador_eventos.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroResponseDTO {

    private int status;
    private String mensagem;
    private LocalDateTime dataHora;
}
package com.leonam.gerenciador_eventos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.LoginRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;
import com.leonam.gerenciador_eventos.dto.response.LoginResponseDTO;
import com.leonam.gerenciador_eventos.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Operações de autenticação do administrador")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Realizar login", description = "Autentica um administrador através do e-mail e senha e retorna um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(
                authService.login(dto));
    }
}
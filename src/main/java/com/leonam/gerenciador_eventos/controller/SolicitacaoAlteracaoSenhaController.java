package com.leonam.gerenciador_eventos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.AtenderSolicitacaoSenhaRequestDTO;
import com.leonam.gerenciador_eventos.dto.request.EsqueciSenhaRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.SolicitacaoAlteracaoSenhaResponseDTO;
import com.leonam.gerenciador_eventos.service.SolicitacaoAlteracaoSenhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitacoes-senha")
@Tag(name = "Solicitação de senha", description = "Operações relacionadas à recuperação e alteração de senha")
public class SolicitacaoAlteracaoSenhaController {

    private final SolicitacaoAlteracaoSenhaService solicitacaoService;

    public SolicitacaoAlteracaoSenhaController(
            SolicitacaoAlteracaoSenhaService solicitacaoService) {

        this.solicitacaoService = solicitacaoService;
    }

    @Operation(summary = "Solicitar alteração de senha", description = "Registra uma solicitação de alteração de senha para um administrador que esqueceu sua senha. A solicitação fica pendente para atendimento por um administrador autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Solicitação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "E-mail inválido ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Void> solicitar(
            @Valid @RequestBody EsqueciSenhaRequestDTO dto) {

        solicitacaoService.solicitar(
                dto.getEmail());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar solicitações pendentes", description = "Lista todas as solicitações de alteração de senha que ainda aguardam atendimento. Este endpoint exige autenticação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitações pendentes retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<SolicitacaoAlteracaoSenhaResponseDTO>> listarPendentes() {

        return ResponseEntity.ok(
                solicitacaoService.listarPendentes());
    }

    @Operation(summary = "Atender solicitação de alteração de senha", description = "Permite que um administrador autenticado defina uma nova senha para o administrador que realizou a solicitação.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Solicitação atendida e senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, senhas não conferem ou solicitação já atendida"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> atender(
            @PathVariable Long id,
            @Valid @RequestBody AtenderSolicitacaoSenhaRequestDTO dto) {

        solicitacaoService.atender(
                id,
                dto);

        return ResponseEntity.noContent().build();
    }
}
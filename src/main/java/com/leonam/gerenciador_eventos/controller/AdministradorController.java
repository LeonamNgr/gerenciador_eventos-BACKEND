package com.leonam.gerenciador_eventos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;
import com.leonam.gerenciador_eventos.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
@Tag(name = "Administradores", description = "Operações de gerenciamento de administradores")
@SecurityRequirement(name = "bearerAuth")
public class AdministradorController {

        private final AdministradorService administradorService;

        public AdministradorController(
                        AdministradorService administradorService) {

                this.administradorService = administradorService;
        }

        @Operation(summary = "Cadastrar administrador", description = "Cadastra um novo administrador. É necessário estar autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @PostMapping
        public ResponseEntity<AdministradorResponseDTO> cadastrar(
                        @Valid @RequestBody AdministradorRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(administradorService.cadastrar(dto));
        }

        @Operation(summary = "Listar administradores", description = "Lista todos os administradores ou filtra pelo nome. É necessário estar autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso."),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @GetMapping
        public ResponseEntity<List<AdministradorResponseDTO>> buscar(
                        @RequestParam(required = false) String nome) {

                if (nome == null || nome.isBlank()) {

                        return ResponseEntity.ok(
                                        administradorService.buscarTodos());
                }

                return ResponseEntity.ok(
                                administradorService.buscarPorNome(nome));
        }

        @Operation(summary = "Editar administrador", description = "Atualiza os dados de um administrador. É necessário estar autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<AdministradorResponseDTO> editar(
                        @PathVariable Long id,
                        @Valid @RequestBody AdministradorRequestDTO dto) {

                return ResponseEntity.ok(
                                administradorService.editar(id, dto));
        }

        @Operation(summary = "Excluir administrador", description = "Exclui um administrador através do seu ID. É necessário estar autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Administrador excluído com sucesso."),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "409", description = "Não foi possível excluir o administrador devido a uma restrição do banco.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @PathVariable Long id) {

                administradorService.deletar(id);

                return ResponseEntity.noContent().build();
        }
}
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
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.EventoRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;
import com.leonam.gerenciador_eventos.dto.response.EventoResponseDTO;
import com.leonam.gerenciador_eventos.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
@Tag(name = "Eventos", description = "Operações de gerenciamento de eventos")
public class EventoController {

        private final EventoService eventoService;

        public EventoController(EventoService eventoService) {
                this.eventoService = eventoService;
        }

        @Operation(summary = "Listar eventos", description = "Lista todos os eventos cadastrados. Esta consulta é pública e não exige autenticação.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Eventos encontrados."),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @GetMapping
        public ResponseEntity<List<EventoResponseDTO>> buscarTodos() {

                return ResponseEntity.ok(
                                eventoService.buscarTodos());
        }

        @Operation(summary = "Buscar evento por ID", description = "Busca um evento através do seu ID. Esta consulta é pública e não exige autenticação.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Evento encontrado."),
                        @ApiResponse(responseCode = "404", description = "Evento não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<EventoResponseDTO> buscarPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                eventoService.buscarPorId(id));
        }

        @Operation(summary = "Cadastrar evento", description = "Cadastra um novo evento. É necessário estar autenticado como administrador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @PostMapping
        @SecurityRequirement(name = "bearerAuth")
        public ResponseEntity<EventoResponseDTO> cadastrar(
                        @Valid @RequestBody EventoRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(eventoService.cadastrar(dto));
        }

        @Operation(summary = "Buscar eventos por administrador", description = "Busca os eventos associados a um administrador. É necessário estar autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Eventos encontrados."),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @GetMapping("/administrador/{administradorId}")
        @SecurityRequirement(name = "bearerAuth")
        public ResponseEntity<List<EventoResponseDTO>> buscarPorAdministrador(
                        @PathVariable Long administradorId) {

                return ResponseEntity.ok(
                                eventoService.buscarPorAdministrador(administradorId));
        }

        @Operation(summary = "Editar evento", description = "Atualiza os dados de um evento. É necessário estar autenticado como administrador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Evento ou administrador não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @PutMapping("/{id}")
        @SecurityRequirement(name = "bearerAuth")
        public ResponseEntity<EventoResponseDTO> editar(
                        @PathVariable Long id,
                        @Valid @RequestBody EventoRequestDTO dto) {

                return ResponseEntity.ok(
                                eventoService.editar(id, dto));
        }

        @Operation(summary = "Excluir evento", description = "Exclui um evento através do seu ID. É necessário estar autenticado como administrador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Evento excluído com sucesso."),
                        @ApiResponse(responseCode = "401", description = "É necessário estar autenticado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Evento não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class)))
        })
        @DeleteMapping("/{id}")
        @SecurityRequirement(name = "bearerAuth")
        public ResponseEntity<Void> deletar(
                        @PathVariable Long id) {

                eventoService.deletar(id);

                return ResponseEntity.noContent().build();
        }
}
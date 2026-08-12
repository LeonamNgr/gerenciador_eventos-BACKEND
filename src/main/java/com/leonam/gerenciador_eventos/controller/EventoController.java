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

import com.leonam.gerenciador_eventos.dto.request.EventoRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.EventoResponseDTO;
import com.leonam.gerenciador_eventos.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;
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

        @Operation(summary = "Cadastrar evento", description = "Cadastra um novo evento associado ao administrador autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @SecurityRequirement(name = "bearerAuth")
        @PostMapping
        public ResponseEntity<EventoResponseDTO> cadastrar(
                        @Valid @RequestBody EventoRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(eventoService.cadastrar(dto));
        }

        @Operation(summary = "Listar ou buscar eventos", description = "Lista todos os eventos ou busca eventos pelo nome. A consulta é pública.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @GetMapping
        public ResponseEntity<List<EventoResponseDTO>> buscar(
                        @RequestParam(required = false) String nome) {

                if (nome == null || nome.isBlank()) {

                        return ResponseEntity.ok(
                                        eventoService.buscarTodos());
                }

                return ResponseEntity.ok(
                                eventoService.buscarPorNome(nome));
        }

        @Operation(summary = "Buscar evento por ID", description = "Busca um evento através do seu ID. Este endpoint é público.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Evento encontrado"),
                        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @GetMapping("/{id}")
        public ResponseEntity<EventoResponseDTO> buscarPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                eventoService.buscarPorId(id));
        }

        @Operation(summary = "Buscar eventos por administrador", description = "Busca todos os eventos associados a um administrador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Eventos encontrados"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @SecurityRequirement(name = "bearerAuth")
        @GetMapping("/administrador/{administradorId}")
        public ResponseEntity<List<EventoResponseDTO>> buscarPorAdministrador(
                        @PathVariable Long administradorId) {

                return ResponseEntity.ok(
                                eventoService.buscarPorAdministrador(administradorId));
        }

        @Operation(summary = "Editar evento", description = "Atualiza os dados de um evento. Qualquer administrador autenticado pode editar qualquer evento.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @SecurityRequirement(name = "bearerAuth")
        @PutMapping("/{id}")
        public ResponseEntity<EventoResponseDTO> editar(
                        @PathVariable Long id,
                        @Valid @RequestBody EventoRequestDTO dto) {

                return ResponseEntity.ok(
                                eventoService.editar(id, dto));
        }

        @Operation(summary = "Excluir evento", description = "Exclui um evento. Qualquer administrador autenticado pode excluir qualquer evento.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Evento excluído com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @SecurityRequirement(name = "bearerAuth")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @PathVariable Long id) {

                eventoService.deletar(id);

                return ResponseEntity.noContent().build();
        }
}
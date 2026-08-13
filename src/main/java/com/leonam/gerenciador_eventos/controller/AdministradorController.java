package com.leonam.gerenciador_eventos.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.AdministradorEdicaoRequestDTO;
import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.request.AlterarSenhaRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

        @Operation(summary = "Cadastrar administrador", description = "Cadastra um novo administrador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @PostMapping
        public ResponseEntity<AdministradorResponseDTO> cadastrar(
                        @Valid @RequestBody AdministradorRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(administradorService.cadastrar(dto));
        }

        @Operation(summary = "Listar administradores", description = "Lista todos os administradores ou filtra pelo nome.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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

        @Operation(summary = "Listar administradores com paginação", description = "Lista os administradores de forma paginada. Permite filtrar pelo nome e ordenar os resultados.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Consulta paginada realizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @GetMapping("/pagina")
        public ResponseEntity<Page<AdministradorResponseDTO>> buscarPagina(

                        @Parameter(description = "Nome ou parte do nome do administrador utilizado para pesquisa.", example = "Leonam") @RequestParam(required = false) String nome,

                        @Parameter(description = "Número da página. A primeira página é 0.", example = "0") @RequestParam(defaultValue = "0") int page,

                        @Parameter(description = "Quantidade de administradores por página.", example = "6") @RequestParam(defaultValue = "6") int size,

                        @Parameter(description = "Campo utilizado para ordenar os administradores.", example = "nome") @RequestParam(defaultValue = "nome") String sort) {

                Pageable pageable = PageRequest.of(
                                page,
                                size,
                                Sort.by(sort));

                return ResponseEntity.ok(
                                administradorService.buscarPagina(
                                                nome,
                                                pageable));
        }

        @Operation(summary = "Buscar administrador por ID", description = "Busca um administrador através do seu ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @GetMapping("/{id}")
        public ResponseEntity<AdministradorResponseDTO> buscarPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                administradorService.buscarPorId(id));
        }

        @Operation(summary = "Editar administrador", description = "Atualiza o nome e o e-mail de um administrador. A senha não é alterada por este endpoint.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @PutMapping("/{id}")
        public ResponseEntity<AdministradorResponseDTO> editar(
                        @PathVariable Long id,
                        @Valid @RequestBody AdministradorEdicaoRequestDTO dto) {

                return ResponseEntity.ok(
                                administradorService.editar(id, dto));
        }

        @Operation(summary = "Alterar senha", description = "Altera a senha do administrador autenticado. É necessário informar a senha atual, a nova senha e a confirmação da nova senha.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou senhas não conferem"),
                        @ApiResponse(responseCode = "401", description = "Senha atual incorreta ou usuário não autenticado"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @PatchMapping("/senha")
        public ResponseEntity<Void> alterarSenha(
                        @Valid @RequestBody AlterarSenhaRequestDTO dto) {

                administradorService.alterarSenha(dto);

                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Excluir administrador", description = "Exclui um administrador através do seu ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Administrador excluído com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
                        @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
                        @ApiResponse(responseCode = "409", description = "Não é possível excluir o administrador porque existem eventos vinculados a ele"),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @PathVariable Long id) {

                administradorService.deletar(id);

                return ResponseEntity.noContent().build();
        }
}
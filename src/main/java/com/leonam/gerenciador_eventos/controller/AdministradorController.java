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
import com.leonam.gerenciador_eventos.service.AdministradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(
            AdministradorService administradorService) {

        this.administradorService = administradorService;
    }

    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(
            @Valid @RequestBody AdministradorRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(administradorService.cadastrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                administradorService.buscarPorId(id));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody AdministradorRequestDTO dto) {

        return ResponseEntity.ok(
                administradorService.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        administradorService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
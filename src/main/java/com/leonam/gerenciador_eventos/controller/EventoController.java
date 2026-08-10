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
import com.leonam.gerenciador_eventos.dto.response.EventoResponseDTO;
import com.leonam.gerenciador_eventos.service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrar(
            @Valid @RequestBody EventoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventoService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> buscarTodos() {

        return ResponseEntity.ok(
                eventoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                eventoService.buscarPorId(id));
    }

    @GetMapping("/administrador/{administradorId}")
    public ResponseEntity<List<EventoResponseDTO>> buscarPorAdministrador(
            @PathVariable Long administradorId) {

        return ResponseEntity.ok(
                eventoService.buscarPorAdministrador(administradorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody EventoRequestDTO dto) {

        return ResponseEntity.ok(
                eventoService.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        eventoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
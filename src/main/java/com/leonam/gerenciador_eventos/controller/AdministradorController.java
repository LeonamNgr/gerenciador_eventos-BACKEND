package com.leonam.gerenciador_eventos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonam.gerenciador_eventos.dto.request.AdministradorRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.AdministradorResponseDTO;
import com.leonam.gerenciador_eventos.service.AdministradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(
            @Valid @RequestBody AdministradorRequestDTO dto) {

        AdministradorResponseDTO response = administradorService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
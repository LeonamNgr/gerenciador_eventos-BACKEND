package com.leonam.gerenciador_eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonam.gerenciador_eventos.entity.Administrador;

public interface AdministradorRepository
        extends JpaRepository<Administrador, Long> {

    boolean existsByEmail(String email);

    Optional<Administrador> findByEmail(String email);

    List<Administrador> findByNomeContainingIgnoreCase(String nome);
}
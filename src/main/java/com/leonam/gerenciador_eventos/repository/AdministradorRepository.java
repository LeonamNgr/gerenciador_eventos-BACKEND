package com.leonam.gerenciador_eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonam.gerenciador_eventos.entity.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Administrador> findByNomeContainingIgnoreCase(String nome);
}
package com.leonam.gerenciador_eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonam.gerenciador_eventos.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByAdministradorId(Long administradorId);

}

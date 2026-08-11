package com.leonam.gerenciador_eventos.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonam.gerenciador_eventos.dto.request.EventoRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.EventoResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.entity.Evento;
import com.leonam.gerenciador_eventos.exception.AdministradorNaoEncontradoException;
import com.leonam.gerenciador_eventos.exception.EventoNaoEncontradoException;
import com.leonam.gerenciador_eventos.repository.EventoRepository;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public EventoResponseDTO cadastrar(EventoRequestDTO dto) {

        Administrador administrador = obterAdministradorLogado();

        Evento evento = new Evento();

        evento.setNomeEvento(dto.getNomeEvento());
        evento.setData(dto.getData());
        evento.setHora(dto.getHora());
        evento.setLocal(dto.getLocal());
        evento.setDescricao(dto.getDescricao());
        evento.setImagem(dto.getImagem());
        evento.setAdministrador(administrador);

        evento = eventoRepository.save(evento);

        return converterParaResponse(evento);
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO buscarPorId(Long id) {

        Evento evento = buscarEntidadePorId(id);

        return converterParaResponse(evento);
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> buscarTodos() {

        return eventoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> buscarPorNome(
            String nomeEvento) {

        return eventoRepository
                .findByNomeEventoContainingIgnoreCase(nomeEvento)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> buscarPorAdministrador(
            Long administradorId) {

        return eventoRepository
                .findByAdministradorId(administradorId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional
    public EventoResponseDTO editar(
            Long id,
            EventoRequestDTO dto) {

        Evento evento = buscarEntidadePorId(id);

        Administrador administrador = obterAdministradorLogado();

        verificarProprietario(evento, administrador);

        evento.setNomeEvento(dto.getNomeEvento());
        evento.setData(dto.getData());
        evento.setHora(dto.getHora());
        evento.setLocal(dto.getLocal());
        evento.setDescricao(dto.getDescricao());
        evento.setImagem(dto.getImagem());

        evento = eventoRepository.save(evento);

        return converterParaResponse(evento);
    }

    @Transactional
    public void deletar(Long id) {

        Evento evento = buscarEntidadePorId(id);

        Administrador administrador = obterAdministradorLogado();

        verificarProprietario(evento, administrador);

        eventoRepository.delete(evento);
    }

    private Evento buscarEntidadePorId(Long id) {

        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(
                        "Evento com ID "
                                + id
                                + " não encontrado."));
    }

    private Administrador obterAdministradorLogado() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof Administrador)) {

            throw new AdministradorNaoEncontradoException(
                    "Administrador autenticado não encontrado.");
        }

        return (Administrador) authentication.getPrincipal();
    }

    private void verificarProprietario(
            Evento evento,
            Administrador administrador) {

        if (evento.getAdministrador() == null
                || !evento.getAdministrador()
                        .getId()
                        .equals(administrador.getId())) {

            throw new EventoNaoEncontradoException(
                    "Evento com ID "
                            + evento.getId()
                            + " não encontrado.");
        }
    }

    private EventoResponseDTO converterParaResponse(
            Evento evento) {

        return new EventoResponseDTO(
                evento.getId(),
                evento.getNomeEvento(),
                evento.getData(),
                evento.getHora(),
                evento.getLocal(),
                evento.getDescricao(),
                evento.getImagem(),
                evento.getAdministrador().getId());
    }
}
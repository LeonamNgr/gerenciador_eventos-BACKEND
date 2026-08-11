package com.leonam.gerenciador_eventos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leonam.gerenciador_eventos.dto.request.EventoRequestDTO;
import com.leonam.gerenciador_eventos.dto.response.EventoResponseDTO;
import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.entity.Evento;
import com.leonam.gerenciador_eventos.exception.EventoNaoEncontradoException;
import com.leonam.gerenciador_eventos.repository.EventoRepository;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final AdministradorService administradorService;

    public EventoService(
            EventoRepository eventoRepository,
            AdministradorService administradorService) {

        this.eventoRepository = eventoRepository;
        this.administradorService = administradorService;
    }

    public EventoResponseDTO cadastrar(EventoRequestDTO dto) {

        Administrador administrador = administradorService.buscarEntidadePorId(
                dto.getAdministradorId());

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

    public EventoResponseDTO buscarPorId(Long id) {

        Evento evento = buscarEntidadePorId(id);

        return converterParaResponse(evento);
    }

    public List<EventoResponseDTO> buscarTodos() {

        return eventoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public List<EventoResponseDTO> buscarPorAdministrador(
            Long administradorId) {

        administradorService.buscarEntidadePorId(administradorId);

        return eventoRepository
                .findByAdministradorId(administradorId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EventoResponseDTO editar(
            Long id,
            EventoRequestDTO dto) {

        Evento evento = buscarEntidadePorId(id);

        Administrador administrador = administradorService.buscarEntidadePorId(
                dto.getAdministradorId());

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

    public void deletar(Long id) {

        Evento evento = buscarEntidadePorId(id);

        eventoRepository.delete(evento);
    }

    private Evento buscarEntidadePorId(Long id) {

        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(
                        "Evento não encontrado."));
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
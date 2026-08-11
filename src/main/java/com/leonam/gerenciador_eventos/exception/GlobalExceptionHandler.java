package com.leonam.gerenciador_eventos.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AdministradorNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> administradorNaoEncontrado(
                        AdministradorNaoEncontradoException exception) {

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ErroResponseDTO(
                                                HttpStatus.NOT_FOUND.value(),
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(EventoNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> eventoNaoEncontrado(
                        EventoNaoEncontradoException exception) {

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ErroResponseDTO(
                                                HttpStatus.NOT_FOUND.value(),
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<ErroResponseDTO> emailJaCadastrado(
                        EmailJaCadastradoException exception) {

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(new ErroResponseDTO(
                                                HttpStatus.CONFLICT.value(),
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErroResponseDTO> dadosInvalidos(
                        MethodArgumentNotValidException exception) {

                String mensagem = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(error -> error.getDefaultMessage())
                                .orElse("Dados inválidos.");

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(new ErroResponseDTO(
                                                HttpStatus.BAD_REQUEST.value(),
                                                mensagem,
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErroResponseDTO> violacaoIntegridade(
                        DataIntegrityViolationException exception) {

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(new ErroResponseDTO(
                                                HttpStatus.CONFLICT.value(),
                                                "Não foi possível concluir a operação devido a uma restrição do banco de dados.",
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponseDTO> erroInterno(
                        Exception exception) {

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErroResponseDTO(
                                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                "Ocorreu um erro interno no servidor.",
                                                LocalDateTime.now()));
        }
}
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

                return criarResposta(
                                HttpStatus.NOT_FOUND,
                                exception.getMessage());
        }

        @ExceptionHandler(EventoNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> eventoNaoEncontrado(
                        EventoNaoEncontradoException exception) {

                return criarResposta(
                                HttpStatus.NOT_FOUND,
                                exception.getMessage());
        }

        @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<ErroResponseDTO> emailJaCadastrado(
                        EmailJaCadastradoException exception) {

                return criarResposta(
                                HttpStatus.CONFLICT,
                                exception.getMessage());
        }

        @ExceptionHandler(CredenciaisInvalidasException.class)
        public ResponseEntity<ErroResponseDTO> credenciaisInvalidas(
                        CredenciaisInvalidasException exception) {

                return criarResposta(
                                HttpStatus.UNAUTHORIZED,
                                exception.getMessage());
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

                return criarResposta(
                                HttpStatus.BAD_REQUEST,
                                mensagem);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErroResponseDTO> violacaoIntegridade(
                        DataIntegrityViolationException exception) {

                return criarResposta(
                                HttpStatus.CONFLICT,
                                "Não foi possível concluir a operação devido a uma restrição do banco de dados.");
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponseDTO> erroInterno(
                        Exception exception) {

                return criarResposta(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Ocorreu um erro interno no servidor.");
        }

        private ResponseEntity<ErroResponseDTO> criarResposta(
                        HttpStatus status,
                        String mensagem) {

                ErroResponseDTO erro = new ErroResponseDTO(
                                status.value(),
                                mensagem,
                                LocalDateTime.now());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }
}
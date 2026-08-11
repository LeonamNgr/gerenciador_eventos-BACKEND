package com.leonam.gerenciador_eventos.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.leonam.gerenciador_eventos.dto.response.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AdministradorNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> administradorNaoEncontrado(
                        AdministradorNaoEncontradoException exception) {

                return criarResposta(
                                HttpStatus.NOT_FOUND,
                                "Administrador não encontrado",
                                exception.getMessage());
        }

        @ExceptionHandler(EventoNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> eventoNaoEncontrado(
                        EventoNaoEncontradoException exception) {

                return criarResposta(
                                HttpStatus.NOT_FOUND,
                                "Evento não encontrado",
                                exception.getMessage());
        }

        @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<ErroResponseDTO> emailJaCadastrado(
                        EmailJaCadastradoException exception) {

                return criarResposta(
                                HttpStatus.CONFLICT,
                                "E-mail já cadastrado",
                                exception.getMessage());
        }

        @ExceptionHandler(EventosVinculadosException.class)
        public ResponseEntity<ErroResponseDTO> eventosVinculados(
                        EventosVinculadosException exception) {

                return criarResposta(
                                HttpStatus.CONFLICT,
                                "Administrador possui eventos vinculados",
                                exception.getMessage());
        }

        @ExceptionHandler(CredenciaisInvalidasException.class)
        public ResponseEntity<ErroResponseDTO> credenciaisInvalidas(
                        CredenciaisInvalidasException exception) {

                return criarResposta(
                                HttpStatus.UNAUTHORIZED,
                                "Credenciais inválidas",
                                exception.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErroResponseDTO> dadosInvalidos(
                        MethodArgumentNotValidException exception) {

                String mensagem = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(error -> "Campo '" + error.getField()
                                                + "': " + error.getDefaultMessage())
                                .orElse("Os dados enviados são inválidos.");

                return criarResposta(
                                HttpStatus.BAD_REQUEST,
                                "Dados inválidos",
                                mensagem);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErroResponseDTO> jsonInvalido(
                        HttpMessageNotReadableException exception) {

                return criarResposta(
                                HttpStatus.BAD_REQUEST,
                                "JSON inválido",
                                "O corpo da requisição possui formato inválido ou contém dados incompatíveis com o esperado.");
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ErroResponseDTO> tipoParametroInvalido(
                        MethodArgumentTypeMismatchException exception) {

                return criarResposta(
                                HttpStatus.BAD_REQUEST,
                                "Parâmetro inválido",
                                "O valor informado para o parâmetro '"
                                                + exception.getName()
                                                + "' possui formato inválido.");
        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ErroResponseDTO> parametroAusente(
                        MissingServletRequestParameterException exception) {

                return criarResposta(
                                HttpStatus.BAD_REQUEST,
                                "Parâmetro obrigatório ausente",
                                "O parâmetro '"
                                                + exception.getParameterName()
                                                + "' é obrigatório.");
        }

        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ErroResponseDTO> tipoMidiaNaoSuportado(
                        HttpMediaTypeNotSupportedException exception) {

                return criarResposta(
                                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                                "Tipo de conteúdo não suportado",
                                "O Content-Type enviado não é suportado por este endpoint.");
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ErroResponseDTO> metodoNaoPermitido(
                        HttpRequestMethodNotSupportedException exception) {

                return criarResposta(
                                HttpStatus.METHOD_NOT_ALLOWED,
                                "Método HTTP não permitido",
                                "O método '"
                                                + exception.getMethod()
                                                + "' não é permitido neste endpoint.");
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ErroResponseDTO> recursoNaoEncontrado(
                        NoResourceFoundException exception) {

                return criarResposta(
                                HttpStatus.NOT_FOUND,
                                "Endpoint não encontrado",
                                "A rota '"
                                                + exception.getResourcePath()
                                                + "' não existe.");
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErroResponseDTO> violacaoIntegridade(
                        DataIntegrityViolationException exception) {

                return criarResposta(
                                HttpStatus.CONFLICT,
                                "Violação de integridade",
                                "Não foi possível concluir a operação porque os dados violam uma regra do banco de dados.");
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponseDTO> erroInterno(
                        Exception exception) {

                return criarResposta(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Erro interno do servidor",
                                "Ocorreu um erro interno ao processar a requisição.");
        }

        private ResponseEntity<ErroResponseDTO> criarResposta(
                        HttpStatus status,
                        String erro,
                        String mensagem) {

                ErroResponseDTO resposta = new ErroResponseDTO(
                                status.value(),
                                erro,
                                mensagem,
                                LocalDateTime.now());

                return ResponseEntity
                                .status(status)
                                .body(resposta);
        }
}
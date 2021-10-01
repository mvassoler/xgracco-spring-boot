package br.com.finchsolucoes.xgracco.domain.handler;

import br.com.finchsolucoes.xgracco.core.constants.TitleValidationConstants;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocaleComponent;
import br.com.finchsolucoes.xgracco.domain.dto.ErrorDetailsDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;
import br.com.finchsolucoes.xgracco.domain.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@ControllerAdvice
@Slf4j
public abstract class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    protected final MessageLocaleComponent messageLocale;

    protected GlobalEntityExceptionHandler(
            final MessageLocaleComponent messageLocale) {
        this.messageLocale = messageLocale;
    }

    /**
     * Handler para tratar BadRequestException, lançada pelos serviços.
     *
     * @param ex      a exception
     * @return ResponseEntity como 400 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> badRequestException(final BadRequestException ex) {
        log.info("M=BadRequestException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(400)
                .exception(exception)
                .statusCode(BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar UnAuthorizedeException, lançada pelos serviços.
     *
     * @param ex      a exception
     * @return ResponseEntity como 401 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(UnAuthorizedeException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> unAuthorizedeException(final UnAuthorizedeException ex) {

        log.info("M=UnAuthorizedeException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(401)
                .exception(exception)
                .statusCode(UNAUTHORIZED)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(UNAUTHORIZED).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar PaymentRequiredException, lançada pelos serviços.
     *
     * @param ex      a exception
     * @return ResponseEntity como 402 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(PaymentRequiredException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> paymentRequiredException(final PaymentRequiredException ex) {
        log.info("M=PaymentRequiredException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(402)
                .exception(exception)
                .statusCode(PAYMENT_REQUIRED)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(PAYMENT_REQUIRED).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar ForbidenException, lançada pelos serviços.
     *
     * @param ex      a exception
     * @return ResponseEntity como 403 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(ForbidenException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> forbidenException(final ForbidenException ex) {
        log.info("M=ForbidenException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(403)
                .exception(exception)
                .statusCode(FORBIDDEN)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(FORBIDDEN).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar EntityNotFoundException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 404 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> entityNotFoundException(final EntityNotFoundException ex) {
        log.info("M=EntityNotFoundException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(404)
                .exception(exception)
                .statusCode(NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar ConflictException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 409 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> conflictException(final ConflictException ex) {
        log.info("M=ConflictException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(409)
                .exception(exception)
                .statusCode(CONFLICT)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(CONFLICT).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar GoneException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 410 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(GoneException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> goneException(final GoneException ex) {
        log.info("M=GoneException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(410)
                .exception(exception)
                .statusCode(GONE)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(GONE).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar PreconditionFailedException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 412 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> preconditionFailedException(final PreconditionFailedException ex) {
        log.info("M=PreconditionFailedException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(412)
                .exception(exception)
                .statusCode(PRECONDITION_FAILED)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(PRECONDITION_FAILED).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar PayloadTooLargeException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 413 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(PayloadTooLargeException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> payloadTooLargeException(final PayloadTooLargeException ex) {
        log.info("M=PayloadTooLargeException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(413)
                .exception(exception)
                .statusCode(PAYLOAD_TOO_LARGE)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(PAYLOAD_TOO_LARGE).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar InternalServerErrorException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 500 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> internalServerErrorException(final InternalServerErrorException ex) {
        log.info("M=InternalServerErrorException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(500)
                .exception(exception)
                .statusCode(INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar NotImplementedException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 501 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> notImplementedException(final NotImplementedException ex) {
        log.info("M=NotImplementedException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(501)
                .exception(exception)
                .statusCode(NOT_IMPLEMENTED)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(NOT_IMPLEMENTED).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar BadGatewayException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 502 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> badGatewayException(final BadGatewayException ex) {
        log.info("M=BadGatewayException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(502)
                .exception(exception)
                .statusCode(BAD_GATEWAY)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(BAD_GATEWAY).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar ServiceUnavailableException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 503 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> serviceUnavailableException(final ServiceUnavailableException ex) {
        log.info("M=ServiceUnavailableException", ex);

        String exception = ClassUtils.getShortClassName(ex.getClass());
        ErrorDetailsDTO error = ErrorDetailsDTO
                .builder()
                .code(503)
                .exception(exception)
                .statusCode(SERVICE_UNAVAILABLE)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(Arrays.asList(error));
    }

    /**
     * Handler para tratar GatewayTimeoutException, lançada pelo CrudService se o id passado no find não existe.
     *
     * @param ex      a exception
     * @return ResponseEntity como 504 e contendo mensagem no corpo do response.
     */
    @ExceptionHandler(GatewayTimeoutException.class)
    public ResponseEntity<?> gatewayTimeoutException(final GatewayTimeoutException ex,WebRequest request) {
        log.info("M=GatewayTimeoutException", ex);


        ErrorDetailsDTO errorDetailsDTO = createProblemBuilder(GATEWAY_TIMEOUT, TitleValidationConstants.ENTIDATE_EM_USO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorDetailsDTO, new HttpHeaders(), GATEWAY_TIMEOUT, request);

    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(final EntidadeEmUsoException ex,WebRequest request) {

        ErrorDetailsDTO errorDetailsDTO = createProblemBuilder(CONFLICT, TitleValidationConstants.ENTIDATE_EM_USO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorDetailsDTO, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                         WebRequest request) {
        ErrorDetailsDTO errorDetailsDTO = createProblemBuilder(NOT_FOUND, TitleValidationConstants.ENTIDADE_NAO_ENCONTRADA, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorDetailsDTO, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(BAD_REQUEST, TitleValidationConstants.ERRO_NEGOCIO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleAdovogadoResponsavel(AdvogadoResponsavelException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.ADVOGADO_RESPONSAVEL, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleArquivoNaoEncontrado(ArquivoNaoEncontradoException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.ARQUIVO_NAO_ENCONTRADO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleAuditoriaSemRegistros(AuditoriaSemRegistrosException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.AUDITORIA_SEM_REGISTROS, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleCnpjNotValidate(CnjNotValidateException ex,
                                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.CNPJ_NAO_VALIDADO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleDataHibernacaoInvalida(DataHibernacaoInvalidaException ex,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.DATA_HIBERNACAO_INVALIDA, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    protected ResponseEntity<Object> handleDecisaoNull(DecisaoNullException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.DECISAO_NULL, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleDiasUteis(DiasUteisException ex,
                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.DIAS_UTEIS, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    protected ResponseEntity<Object> handleGenericError(GenericErrorException ex,
                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.ERRO_GENERICO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    protected ResponseEntity<Object> handleIdConflict(IdConflictException ex,
                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.ID_CONFLITO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleNoFilaActive(NoFilaActiveException ex,
                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.SEM_FILA_ATIVA, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    protected ResponseEntity<Object> handleTarefaDuplicada(TarefaDuplicadaException ex,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.TAREFA_DUPLICADA, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    protected ResponseEntity<Object> handleUnauthorized(UnauthorizedAccessException ex,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.SEM_AUTORIZACAO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    protected ResponseEntity<Object> handleUsuarioBloqueado(UsuarioBloqueadoException ex,
                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.USUARIO_BLOQUEADO, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    protected ResponseEntity<Object> handleValorSentenca(ValorSentencaNullException ex,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetailsDTO problem = createProblemBuilder(status, TitleValidationConstants.VALOR_SETENCA, ex.getMessage(), request.getContextPath())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }



    private ErrorDetailsDTO.ErrorDetailsDTOBuilder createProblemBuilder(HttpStatus status,
                                                        String title, String detail, String contextPath) {

        return ErrorDetailsDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(contextPath)
                .title(title)
                .detail(detail);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.info("M=handleBindException", ex);
        return getListErros(ex.getBindingResult(), ClassUtils.getShortClassName(ex.getClass()), ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.info("M=handleMethodArgumentNotValid", ex.getMessage());
        return getListErros(ex.getBindingResult(), ClassUtils.getShortClassName(ex.getClass()), ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.info("M=handleHttpMessageNotReadable", ex);
        List<ErrorDetailsDTO> errors = new ArrayList<>();
        Throwable rootThowable = ex.getCause();
        ((JsonMappingException) rootThowable).getPath().forEach(e -> {
                    String exception = ClassUtils.getShortClassName(ex.getClass());
//                    errors.add(new ErrorDetailsDTO(e.getFieldName(), exception,
//                            messageLocale.validationMessageSource(FIELD_INVALID_VALUE), BAD_REQUEST, BAD_REQUEST.value()));
                }
        );
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        log.info("M=handleMissingServletRequestParameter", ex);
        String exception = ClassUtils.getShortClassName(ex.getClass());
//        ErrorDetailsDTO error = new ErrorDetailsDTO(ex.getParameterName(), exception,
//                messageLocale.validationMessageSource(FIELD_INVALID_VALUE), BAD_REQUEST, BAD_REQUEST.value());
//        return ResponseEntity.status(BAD_REQUEST).body(Arrays.asList(error));
        return null;
    }

    //TODO Resolver o problema do construtor
    private ResponseEntity<Object> getListErros(
            final BindingResult bindingResult,
            final String shortClassName,
            final Exception ex
    ) {
        List<ErrorDetailsDTO> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            String exception = shortClassName;
            //errors.add(new ErrorDetailsDTO(error.getField(), exception, error.getDefaultMessage(), BAD_REQUEST, BAD_REQUEST.value()));
            log.error(ex.getMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }
}
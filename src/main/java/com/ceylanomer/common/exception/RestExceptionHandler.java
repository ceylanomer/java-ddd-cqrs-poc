package com.ceylanomer.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ceylanomer.common.controller.BaseController;
import com.ceylanomer.common.response.ErrorResponse;
import com.ceylanomer.common.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends BaseController {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorResponse> handleException(Exception exception, Locale locale) {
        log.error("Unhandled exception occurred", exception);
        return createErrorResponseFromMessageSource("general.error", locale);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException methodNotSupportedException, Locale locale) {
        log.error("Method not supported exception occurred", methodNotSupportedException);
        return createErrorResponseFromMessageSource("method.not.supported", locale);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMessageNotReadableException(HttpMessageNotReadableException messageNotReadableException, Locale locale) {
        log.error("Message not readable exception occurred", messageNotReadableException);
        return createErrorResponseFromMessageSource("message.not.readable", locale);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleRequestPropertyBindingException(WebExchangeBindException webExchangeBindException, Locale locale) {
        log.error("Web exchange bind exception occurred", webExchangeBindException);
        return createFieldErrorResponse(webExchangeBindException.getBindingResult(), locale);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleRequestPropertyBindingException(BindException bindException, Locale locale) {
        log.error("Bind exception occurred", bindException);
        return createFieldErrorResponse(bindException.getBindingResult(), locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleInvalidArgumentException(MethodArgumentNotValidException methodArgumentNotValidException, Locale locale) {
        log.error("Method argument not valid exception occurred", methodArgumentNotValidException);
        return createFieldErrorResponse(methodArgumentNotValidException.getBindingResult(), locale);
    }

    @ExceptionHandler(ApiBusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse> handleApiBusinessException(ApiBusinessException apiBusinessException, Locale locale) {
        log.error("Business exception occurred: {}", apiBusinessException.getKey(), apiBusinessException);
        return createErrorResponseFromMessageSource(apiBusinessException.getKey(), locale, apiBusinessException.getArgs());
    }

    @ExceptionHandler(ApiDataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorResponse> handleApiDataNotFoundException(ApiDataNotFoundException apiDataNotFoundException, Locale locale) {
        log.error("Data not found exception occurred: {}", apiDataNotFoundException.getKey(), apiDataNotFoundException);
        return createErrorResponseFromMessageSource(apiDataNotFoundException.getKey(), locale, apiDataNotFoundException.getArgs());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorResponse> handleNoSuchElementException(NoSuchElementException noSuchElementException, Locale locale) {
        log.error("No such element exception occurred", noSuchElementException);
        return createErrorResponseFromMessageSource("data.not.found", locale);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException, Locale locale) {
        log.error("Empty result data access exception occurred", emptyResultDataAccessException);
        return createErrorResponseFromMessageSource("data.not.found", locale);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, Locale locale) {
        log.error("Method argument type mismatch exception occurred", methodArgumentTypeMismatchException);
        return createErrorResponseFromMessageSource("argument.type.mismatch", locale);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException, Locale locale) {
        log.error("Illegal argument exception occurred", illegalArgumentException);
        return respond(new ErrorResponse("INVALID_ARGUMENT", illegalArgumentException.getMessage()));
    }

    private Response<ErrorResponse> createFieldErrorResponse(BindingResult bindingResult, Locale locale) {
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String errorMessage = messageSource.getMessage(fieldError, locale);
            errorMessages.add(errorMessage);
        });
        
        String errorMessage = String.join(", ", errorMessages);
        return respond(new ErrorResponse("VALIDATION_ERROR", errorMessage));
    }

    private Response<ErrorResponse> createErrorResponseFromMessageSource(String key, Locale locale, String... args) {
        List<String> messages = retrieveLocalizationMessage(key, locale, args);
        String message = String.join(", ", messages);
        return respond(new ErrorResponse(key, message));
    }

    private List<String> retrieveLocalizationMessage(String key, Locale locale, String... args) {
        List<String> messages = new ArrayList<>();
        try {
            String message = messageSource.getMessage(key, args, locale);
            messages.add(message);
        } catch (Exception e) {
            log.error("Error retrieving message for key: {}", key, e);
            messages.add(key);
        }
        return messages;
    }
} 
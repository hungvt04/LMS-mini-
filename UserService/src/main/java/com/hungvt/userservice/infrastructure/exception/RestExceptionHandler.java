package com.hungvt.userservice.infrastructure.exception;

import com.hungvt.userservice.infrastructure.common.model.request.ErrorResponse;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import com.hungvt.userservice.infrastructure.constant.MappingUrl;
import com.hungvt.userservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    private static ErrorResponse getErrorResponse(MethodArgumentNotValidException ex,
                                                  String message,
                                                  String detailMessage) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        int code = 400;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        return new ErrorResponse(message, detailMessage, errors, code, moreInformation);
    }

    private String getMessage(String key) {
        return messageSource.getMessage(
                key,
                null,
                "Default message",
                LocaleContextHolder.getLocale());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {

        String message = getMessage("NoHandleFoundException.message") + ex.getHttpMethod() + " " + ex.getRequestURL();
        String detailMessage = ex.getLocalizedMessage();
        int code = 404;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.NOT_FOUND);
        return Helper.createResponseEntity(responseObject);
    }

    private String getMessageFromHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        StringBuilder message = new StringBuilder();
        message.append(exception.getMessage()).append(" ")
                .append(getMessage("HttpRequestMethodNotSupportedException.message"));

        for (HttpMethod method : Objects.requireNonNull(exception.getSupportedHttpMethods())) {
            message.append(" '").append(method).append("'");
        }
        return message.toString();
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {

        String message = getMessageFromHttpRequestMethodNotSupportedException(ex);
        String detailMessage = ex.getLocalizedMessage();
        int code = 405;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.METHOD_NOT_ALLOWED);
        return Helper.createResponseEntity(responseObject);
    }

    private String getMessageFromHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {

        StringBuilder message = new StringBuilder();
        message.append(exception.getMessage()).append(" ")
                .append(getMessage("HttpRequestMethodNotSupportedException.message"));

        for (MediaType type : exception.getSupportedMediaTypes()) {
            message.append(type).append(", ");
        }
        return message.substring(0, message.toString().length() - 2);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {

        String message = getMessageFromHttpMediaTypeNotSupportedException(ex);
        String detailMessage = ex.getLocalizedMessage();
        int code = 415;
        String moreInformation = MappingUrl.API_AUTH + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return Helper.createResponseEntity(responseObject);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        String message = getMessage("MethodArgumentNotValidException.exception");
        String detailMessage = ex.getLocalizedMessage();
        ErrorResponse response = getErrorResponse(ex, message, detailMessage);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.BAD_REQUEST);
        return Helper.createResponseEntity(responseObject);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {

        String message = getMessage("RuntimeException.message");
        String detailMessage = exception.getLocalizedMessage();
        int code = 409;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.CONFLICT);
        return Helper.createResponseEntity(responseObject);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {

        String message = "Bạn không có quyền truy cập!!!";
        String detailMessage = exception.getLocalizedMessage();
        int code = 403;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.FORBIDDEN);
        return Helper.createResponseEntity(responseObject);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception exception) {

        String message = getMessage("Exception.message");
        String detailMessage = exception.getLocalizedMessage();
        log.error("❌ Exception: {}", detailMessage, exception);
        int code = 500;
        String moreInformation = MappingUrl.API_URL_EXCEPTION + "/" + code;

        ErrorResponse response = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        ResponseObject responseObject = ResponseObject.ofData(
                response,
                detailMessage,
                HttpStatus.INTERNAL_SERVER_ERROR);
        return Helper.createResponseEntity(responseObject);
    }

}

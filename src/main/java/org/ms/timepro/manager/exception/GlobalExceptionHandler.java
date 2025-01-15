package org.ms.timepro.manager.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * GlobalExceptionHandler - Manejador de excepciones para api de seguridad - Spring Boot main class
 *
 * @author Enigma Team
 * @since 0.0.1
 * @version jdk-11
 */
@Slf4j
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {

	@Nullable
	@ExceptionHandler({ UserNotFoundException.class, MethodArgumentNotValidException.class, ContentNotAllowedException.class, })
	public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();

		log.error("Manejo " + ex.getClass().getSimpleName() + " debido a " + ex.getMessage());

		if (ex instanceof UserNotFoundException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			UserNotFoundException unfe = (UserNotFoundException) ex;
			return handleCommonException(unfe, headers, status, request);
		} else if (ex instanceof ContentNotAllowedException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			ContentNotAllowedException cnae = (ContentNotAllowedException) ex;
			return handleContentNotAllowedException(cnae, headers, status, request);

		} else if (ex instanceof MethodArgumentNotValidException) {
			BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
			List<FieldError> fieldErrors = result.getFieldErrors();
			List<String> errorMessage = new ArrayList<>();
			fieldErrors.forEach(f -> errorMessage.add(f.getField() + " " + f.getDefaultMessage()));
			HttpStatus status = HttpStatus.BAD_REQUEST;
			MethodArgumentNotValidException excp = (MethodArgumentNotValidException) ex;
			return handleCommonException(excp, headers, status, request, errorMessage);

		} else {
			if (log.isWarnEnabled()) {
				log.warn("Unknown exception type: " + ex.getClass().getName());
			}

			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, null, headers, status, request);
		}
	}

	@ExceptionHandler({ UserNotAuthException.class, TokenValidationException.class , ApplicationValidationException.class })
	@Nullable
	public final ResponseEntity<ApiError> handleNotAuthException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		log.error("Manejo " + ex.getClass().getSimpleName() + " debido a " + ex.getMessage());

		if (ex instanceof UserNotAuthException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			UserNotAuthException unfe = (UserNotAuthException) ex;
			return handleCommonException(unfe, headers, status, request);
		}else if (ex instanceof TokenValidationException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			UserNotAuthException unfe = (UserNotAuthException) ex;
			return handleCommonException(unfe, headers, status, request);
		} else if (ex instanceof ApplicationValidationException) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			ApplicationValidationException unfe = (ApplicationValidationException) ex;
			return handleCommonException(unfe, headers, status, request);
		} else {
			if (log.isWarnEnabled()) {
				log.warn("Unknown exception type: " + ex.getClass().getName());
			}

			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, null, headers, status, request);
		}

	} 

	protected ResponseEntity<ApiError> handleCommonException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		log.error("[handleCommonException]HttpHeaders --> " + headers.toString());
		log.error("[handleCommonException]HttpStatus --> " + status.toString());
		log.error("[handleCommonException]error --> " + ex.getLocalizedMessage(), ex);
		return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
	}

	protected ResponseEntity<ApiError> handleExistException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		log.error("[handleCommonException]HttpHeaders --> " + headers.toString());
		log.error("[handleCommonException]HttpStatus --> " + status.toString());
		log.error("[handleCommonException]error --> " + ex.getLocalizedMessage(), ex);
		return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
	}

	protected ResponseEntity<ApiError> handleCommonException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request, List<String> errors) {
		log.error("[handleCommonException]HttpHeaders --> " + headers.toString());
		log.error("[handleCommonException]HttpStatus --> " + status.toString());
		return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
	}

	protected ResponseEntity<ApiError> handleContentNotAllowedException(ContentNotAllowedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errorMessages = ex.getErrors().stream()
				.map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
				.collect(Collectors.toList());
		log.error("[handleContentNotAllowedException]HttpHeaders --> " + headers.toString());
		log.error("[handleContentNotAllowedException]HttpStatus --> " + status.toString());
		log.error("[handleContentNotAllowedException]error --> " + ex.getLocalizedMessage(), ex);
		return handleExceptionInternal(ex, new ApiError(status, errorMessages), headers, status, request);
	}

	protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, @Nullable ApiError body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}
}

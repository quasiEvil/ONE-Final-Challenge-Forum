package br.com.alura.forum.infra.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;


import jakarta.validation.ValidationException;

import java.util.List;

@RestControllerAdvice
public class ErrorHandlerConfig {

    @ExceptionHandler(ObjectNotFoundExpection.class)
    public ResponseEntity handle404Error(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle400Error(MethodArgumentNotValidException ex) {
        List<ValidationErrorDetails> errors = ex.getFieldErrors().stream()
                .map(ValidationErrorDetails::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleBusinessRuleValidation(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("*** Credenciais inválidas ***");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthenticationFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("*** Falha na autenticação ***");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handleAuthenticationFailure2() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("*** Usuário ou senha errados ***");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("*** Acesso negado ***");
    }

    @ExceptionHandler(UserPermissionException.class)
    public ResponseEntity handleUserPermissionDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("*** Acesso Negado. Usuário sem permissão. ***");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle500Error(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("*** Erro: " + ex.getLocalizedMessage() + " ***");
    }

    private record ValidationErrorDetails(String field, String message) {
        public ValidationErrorDetails(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

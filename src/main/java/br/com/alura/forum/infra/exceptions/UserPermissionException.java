package br.com.alura.forum.infra.exceptions;

public class UserPermissionException extends RuntimeException {
    public UserPermissionException(String message) {
        super(message);
    }
}

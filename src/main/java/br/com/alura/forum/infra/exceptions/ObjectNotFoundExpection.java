package br.com.alura.forum.infra.exceptions;

public class ObjectNotFoundExpection extends RuntimeException{
    public ObjectNotFoundExpection(String message) {
        super(message);
    }
}
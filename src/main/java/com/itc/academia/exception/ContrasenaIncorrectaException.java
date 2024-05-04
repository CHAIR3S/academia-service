package com.itc.academia.exception;

public class ContrasenaIncorrectaException extends RuntimeException{

    public ContrasenaIncorrectaException() {
        super("La contraseña es inválida.");
    }

}

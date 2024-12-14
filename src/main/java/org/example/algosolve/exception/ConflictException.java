package org.example.algosolve.exception;

public class ConflictException extends RuntimeException{
    public ConflictException() {
        this("리소스가 중복됩니다");
    }

    public ConflictException(String message) {
        super(message);
    }
}

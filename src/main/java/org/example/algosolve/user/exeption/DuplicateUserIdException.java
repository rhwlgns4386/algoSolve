package org.example.algosolve.user.exeption;

import org.example.algosolve.exception.ConflictException;

public class DuplicateUserIdException extends ConflictException {
    public DuplicateUserIdException() {
        super("사용자아이디가 이미 존재 합니다");
    }
}

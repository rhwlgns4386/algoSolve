package org.example.algosolve.user.controller;

import lombok.Data;

@Data
public class IdDto {
    private final String id;
    public IdDto(String id) {
        this.id = id;
    }
}

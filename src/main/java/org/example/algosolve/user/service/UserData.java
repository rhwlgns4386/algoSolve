package org.example.algosolve.user.service;

import lombok.Data;
import lombok.Getter;
import org.example.algosolve.user.domain.User;

@Getter
public final class UserData {
    private String id;

    public UserData(User user) {
        this(user.getUserId());
    }

    public UserData(String id) {
        this.id = id;
    }
}

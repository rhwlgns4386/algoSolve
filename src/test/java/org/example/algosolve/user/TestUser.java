package org.example.algosolve.user;

import org.example.algosolve.user.domain.User;

public class TestUser {
    public static final User USER = new User("test1", "testPassword","test" ,1,new TestUserPasswordEncoder());
}

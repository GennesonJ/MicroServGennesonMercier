package com.example.AuthService;

public class PasswordIncorrectExeption extends Throwable {
    public PasswordIncorrectExeption(Long id) {
        super("Password invalide for user: "+ id);
    }
}

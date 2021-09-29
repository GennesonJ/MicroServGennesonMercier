package com.example.AuthService;


public class TokenNotValidException extends RuntimeException {
    TokenNotValidException(Token token){
        super("Token invalide : "+ token);
    }
}

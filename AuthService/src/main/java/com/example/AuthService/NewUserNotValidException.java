package com.example.AuthService;


public class NewUserNotValidException extends RuntimeException {
    NewUserNotValidException(long id){
        super("User invalide, ou l'id est déjà utilisé : "+ id);
    }
}

package com.example.AuthService;


public class NewUserNotFoundException extends RuntimeException {
    NewUserNotFoundException(long id){
        super("Could not find user : "+ id);
    }
}

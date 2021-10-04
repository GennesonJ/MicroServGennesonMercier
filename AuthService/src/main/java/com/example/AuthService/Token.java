package com.example.AuthService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

public class Token {

    public String token;
    public long userId;
    private LocalDateTime timeLimit;

    final char[] allowedChars = "ABCDEFGHIJKLMOPQRSTUVWXYZ0123456789".toCharArray();

    public Token(Long id) {
        this.userId=id;
        this.token=generateToken();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(LocalDateTime timeLimit) {
        this.timeLimit = timeLimit;
    }

    private String generateToken() {
        Random random = new SecureRandom();
        StringBuilder token = new StringBuilder();

        for (int i=0; i<10; i++ ){
            token.append(allowedChars[random.nextInt(allowedChars.length)]);
        }
        return token.toString();
    }

    public boolean isValid(){
        LocalDateTime now =LocalDateTime.now();
        return this.timeLimit.isAfter(now);
    }

}

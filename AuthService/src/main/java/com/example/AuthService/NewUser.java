package com.example.AuthService;

import javax.validation.constraints.NotNull;

public class NewUser {

    private long id;

    @NotNull
    private String password;

    public NewUser(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkpassword(String password){
        if(this.password.equals(password))
            return true;
        else
            return false;
    }
}

package com.example.userservice;

import org.springframework.boot.context.properties.bind.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class User {

    private long id;

    @NotNull(message = "Please provide a name")
    private String name;

    @Email
    private String mail;

    public User(long id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

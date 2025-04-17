package com.unicesumar.model;

import java.util.UUID;

// Classe que representa a entidade "users"
public class User extends Entity {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(UUID uuid, String name, String email, String password) {
        super(uuid);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return String.format("%s | %s", name, email);
    }
}

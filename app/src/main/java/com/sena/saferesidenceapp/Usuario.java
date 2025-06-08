package com.sena.saferesidenceapp;

public class Usuario {
    public String name;
    public long dni;
    public long phone;
    public String email;
    public String apartment;
    public String username;
    public String role;

    // Constructor vacío requerido por Firebase
    public Usuario() {}

    public Usuario(String name, long dni, long phone, String email,
                   String apartment, String username, String role) {
        this.name = name;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.apartment = apartment;
        this.username = username;
        this.role = role;
    }
}

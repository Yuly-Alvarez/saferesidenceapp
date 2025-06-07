package com.sena.saferesidenceapp;

public class Usuario {
    public String name, dni, phone, email, apartment, username, role;

    // Constructor vacío requerido por Firebase
    public Usuario() {}

    public Usuario(String name, String dni, String phone, String email,
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

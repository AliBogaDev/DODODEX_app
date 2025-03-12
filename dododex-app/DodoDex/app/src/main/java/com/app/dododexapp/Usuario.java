package com.app.dododexapp;

public class Usuario {
    private String nombreUsuario;
    private String contraseña;
    private String email;
    private String telefono;


    public Usuario(String nombreUsuario, String contraseña, String email, String telefono) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

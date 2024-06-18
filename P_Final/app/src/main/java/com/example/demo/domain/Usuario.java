package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "Name")
    private String name = "";

    @Column(name = "Apellido")
    private String last_name = "";

    @Column(name = "Email")
    private String email = "";

    @Column(name = "Contraseña")
    private String password = "";

    @Column(name = "Rol")
    private String rol = "";

    public Usuario() {
    }

    public Usuario(long id, String name, String last_name, String email, String password, String rol) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;

        if (rol.equals("usuario") || rol.equals("administrador") || rol.equals("visitante") || rol.equals("editor")) {
            this.rol = rol;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", name=" + name + ", last_name=" + last_name + ", email=" + email + ", password="
                + password + ", rol=" + rol + "]";
    }

    

}

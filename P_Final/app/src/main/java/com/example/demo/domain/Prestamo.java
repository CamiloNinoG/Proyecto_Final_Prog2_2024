package com.example.demo.domain;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Prestamo {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    private String isbn = "";
    private long id_user = 0;
    private LocalDate date;
    
    public Prestamo() {
    }

    public Prestamo(long id, String isbn, long id_user, LocalDate date) {
        this.id = id;
        this.isbn = isbn;
        this.id_user = id_user;
        this.date = date;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public long getId_user() {
        return id_user;
    }
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + id + ", isbn=" + isbn + ", id_user=" + id_user + ", date=" + date + "]";
    }

    
    

    
}

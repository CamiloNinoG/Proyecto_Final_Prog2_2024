package com.example.demo.domain;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "Titulo")
    private String title = "";

    @Column(name = "Autor")
    private String autor = "";

    @Column(name = "Genero")
    private String gender = "";

    @Column(name = "Fecha publicación")
    private LocalDate date;

    @Column(name = "ISBN")
    private String isbn = "";

    @Column(name = "Cantidad disponible")
    private int stock = 0;

    @Column(name = "Descripción")
    private String description = "";

    public Libro(String title, String autor, String gender, LocalDate date, String isbn, int stock, String description) {
        this.title = title;
        this.autor = autor;
        this.gender = gender;
        this.date = date;
        this.isbn = isbn;
        this.stock = stock;
        this.description = description;
    }

    public Libro(long id, String title, String autor, String gender, LocalDate date, String isbn, int stock, String description) {
        this.id = id;
        this.title = title;
        this.autor = autor;

        if (gender.equals("Masculino") || gender.equals("Femenino") || gender.equals("No binario")) {
            this.gender = gender;
        }
        this.date = date;
        this.isbn = isbn;
        this.stock = stock;
        this.description = description;
    }

    public Libro() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nID : " + id + "\nTitulo : " + title + "\nAutor : " + autor + "\nGenero : " + gender
                + "\nFecha de publicacion : " + date
                + "\nISBN : " + isbn + "\nCantidad disponible : " + stock + "\nDescripcion : " + description;
    }
}

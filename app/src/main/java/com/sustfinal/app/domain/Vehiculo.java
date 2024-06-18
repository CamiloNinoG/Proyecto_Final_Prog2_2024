package com.sustfinal.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "Marca")
    private String marca = "";

    @Column(name = "Modelo")
    private String modelo = "";

    @Column(name = "Año")
    private int año = 0;

    @Column(name = "Color")
    String color = "";

    @Column(name = "Precio")
    private double precio = 0;

    @Column(name = "Tipo Vehiculo")
    private String tipo = "";

    @Column(name = "Consecionario")
    private String consecionario = "";

    public Vehiculo(long id, String marca, String modelo, int año, String color, double precio, String tipo,
            String consecionario) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.color = color;
        this.precio = precio;
        if (tipo.equals("Sedan") || tipo.equals("SUV") || tipo.equals("Camioneta")) {

            this.tipo = tipo;
        }
        this.consecionario = consecionario;
    }

    public Vehiculo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConsecionario() {
        return consecionario;
    }

    public void setConsecionario(String consecionario) {
        this.consecionario = consecionario;
    }

}

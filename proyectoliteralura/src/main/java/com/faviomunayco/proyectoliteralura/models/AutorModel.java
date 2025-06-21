package com.faviomunayco.proyectoliteralura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")
public class AutorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nacimiento")
    Integer nacimiento;
    @Column(name = "fallece")
    Integer fallece;
    @Column(name = "nombre", unique = true)
    String nombre;

    @ManyToMany(mappedBy = "authors")
    private List<LibroModel> libros;

    @Version
    private Integer version;


    public AutorModel() {
    }

    public AutorModel(Integer nacimiento, Integer fallece, String nombre) {

        this.nacimiento = nacimiento;
        this.fallece = fallece;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }


    public Integer getnacimiento() {
        return nacimiento;
    }

    public void setnacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getfallece() {
        return fallece;
    }

    public void setfallece(Integer fallece) {
        this.fallece = fallece;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor: " + "id=" + id + ", Año nacimiento=" + nacimiento + ", Año de fallecimiento=" + fallece + ", Nombres='" + nombre + '\'' + '\n';
    }
}

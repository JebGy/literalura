package com.faviomunayco.proyectoliteralura.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "LibroModel")
public class LibroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "titulo")
    String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "languages", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "languages")
    List<String> languages;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    List<AutorModel> authors;

    @Version
    private Integer version;

    public LibroModel() {
    }

    public LibroModel(Long id, String title, List<String> languages, List<AutorModel> authors) {
        this.id = id;
        this.title = title;
        this.languages = languages;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<AutorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AutorModel> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "LibroModel: " + "id=" + id + ", titulo='" + title + '\'' + ", lenguages=" + languages + ", autores=" + authors + '\n';
    }
}

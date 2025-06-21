package com.faviomunayco.proyectoliteralura.repository;

import com.faviomunayco.proyectoliteralura.models.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioLibro extends JpaRepository<LibroModel, Long> {
    List<LibroModel> findByLanguagesContaining(String language);

}

package com.faviomunayco.proyectoliteralura.repository;

import com.faviomunayco.proyectoliteralura.models.AutorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioAutor extends JpaRepository<AutorModel, Long> {

    @Query("SELECT a FROM AutorModel a WHERE a.nacimiento <= :anio AND (a.fallece IS NULL OR a.fallece > :anio)")
    List<AutorModel> findAutoresVivosEnAnio(@Param("anio") Integer anio);
}

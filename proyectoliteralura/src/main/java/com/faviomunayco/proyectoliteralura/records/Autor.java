package com.faviomunayco.proyectoliteralura.records;


import com.fasterxml.jackson.annotation.JsonAlias;

public record Autor(@JsonAlias("birth_year") Integer nacimiento, @JsonAlias("death_year") Integer fallece, @JsonAlias("name")String nombre) {
}

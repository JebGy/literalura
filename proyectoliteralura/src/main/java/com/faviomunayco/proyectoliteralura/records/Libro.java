package com.faviomunayco.proyectoliteralura.records;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(Long id, String title, List<Autor> authors, List<String> summaries, List<String> languages) {
}

package com.faviomunayco.proyectoliteralura.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaApi(List<Libro> results) {
}

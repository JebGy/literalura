package com.faviomunayco.proyectoliteralura.services;

import com.faviomunayco.proyectoliteralura.connections.ApiConexion;
import com.faviomunayco.proyectoliteralura.dto.AutorDTO;
import com.faviomunayco.proyectoliteralura.dto.LibroDTO;
import com.faviomunayco.proyectoliteralura.models.AutorModel;
import com.faviomunayco.proyectoliteralura.models.LibroModel;
import com.faviomunayco.proyectoliteralura.records.RespuestaApi;
import com.faviomunayco.proyectoliteralura.repository.RepositorioAutor;
import com.faviomunayco.proyectoliteralura.repository.RepositorioLibro;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    private String API = "https://gutendex.com/books/?search=";
    private ApiConexion conexion = new ApiConexion();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner lector = new Scanner(System.in);
    private RepositorioLibro repositorioLibro;
    private RepositorioAutor repositorioAutor;

    public Menu(RepositorioLibro repositorioLibro, RepositorioAutor repositorioAutor) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void consultarApi(String titulo) {
        var respuestaApi = conexion.obtenerDatosApi(API + titulo);
        var datos = conversorDatos.transformarDatos(respuestaApi, RespuestaApi.class);
        convertirLibro(datos);
        System.out.println(datos.results().get(0));
    }

    public void showMenu() {
        var literaluraEstaCorriendo = true;
        while (literaluraEstaCorriendo) {
            System.out.println("""
                    ****************************************************
                    *              Bienvenido a LITERALURA             *
                    ****************************************************
                    *  1) Buscar libro por titulo                      *
                    *  2) Listar libros registrados                    *
                    *  3) Listar autores registrados                   *
                    *  4) Listar autores vivos en un determinado año   *
                    *  5) Listar libros por idioma                     *
                    *  6) Salir                                        *
                    ****************************************************
                    """);
            System.out.println("*> Selecciona una opción: ");
            var opcion = lector.nextInt();
            lector.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosAlmacenados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnUnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPoridioma();
                    break;
                case 6:
                    literaluraEstaCorriendo = false;
                    break;
                default:
                    System.out.println("****************************************************");
                    System.out.println("*                Opción no válida                  *");
                    System.out.println("****************************************************\n\n");
                    break;
            }
        }
    }

    public void buscarLibroPorTitulo() {
        System.out.println("*> Ingresa el titulo: ");
        var libro = lector.nextLine();

        if (libro.isBlank()) {
            System.out.println("No ingresaste ningún título.");
            return;
        }

        var libroABuscar = Arrays.stream(libro.toLowerCase().split(" ")).collect(Collectors.joining("%20"));
        System.out.println(libroABuscar);

        consultarApi(libroABuscar);
    }


    @Transactional
    private void convertirLibro(RespuestaApi respuestaApi) {
        var libroRecord = respuestaApi.results().get(0);
        var autores = libroRecord.authors().stream().map(a -> new AutorModel(a.nacimiento(), a.fallece(), a.nombre())).collect(Collectors.toList());
        autores.stream().forEach(autorModel -> repositorioAutor.save(autorModel));
        this.repositorioLibro.save(new LibroModel(null, libroRecord.title(), libroRecord.languages(), autores));
    }

    @Transactional
    public void listarLibrosAlmacenados() {
        var librosRegistrados = this.repositorioLibro.findAll().stream().map(lm -> new LibroDTO(lm.getId(), lm.getTitle(), lm.getLanguages(), lm.getAuthors().stream().map(am -> new AutorDTO(am.getId(), am.getnacimiento(), am.getfallece(), am.getnombre())).collect(Collectors.toList()))).collect(Collectors.toList());
        System.out.println("*> Libros almacenados: ");
        librosRegistrados.stream().forEach(l -> System.out.printf("""
                --------------------------------------------------
                - Libro                                          -
                --------------------------------------------------
                - Id: %s
                - Título: %s
                - Autores: %s
                - Idiomas: %s
                ---------------------------------------------------
                """, l.id(), l.title(), l.authors().stream().map(a -> a.nombre()).collect(Collectors.joining(",")), String.join(",", l.languages())));
    }

    @Transactional
    public void listarAutoresRegistrados() {
        var autoresRegistrados = this.repositorioAutor.findAll().stream().map(am -> new AutorDTO(am.getId(), am.getnacimiento(), am.getfallece(), am.getnombre())).collect(Collectors.toList());
        autoresRegistrados.stream().forEach(ar -> System.out.printf("""
                --------------------------------------------------
                - Autor                                          -
                --------------------------------------------------
                - Id: %s
                - Fecha de nacimiento: %s
                - Fecha de fallecimiento: %s
                - Nombres completos: %s
                ---------------------------------------------------
                """, ar.id(), ar.nacimiento(), ar.fallece(), ar.nombre()));
    }

    public void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("*> Ingresa el año a consultar: ");
        var anioABuscar = lector.nextInt();
        lector.nextLine();
        var autoresVivos = this.repositorioAutor.findAutoresVivosEnAnio(anioABuscar).stream().map(a -> new AutorDTO(a.getId(), a.getnacimiento(), a.getfallece(), a.getnombre())).collect(Collectors.toList());

        autoresVivos.stream().forEach(av -> System.out.printf("""
                --------------------------------------------------
                - Autor: VIVO                                    -
                --------------------------------------------------
                - Id: %s
                - Fecha de nacimiento: %s
                - Fecha de fallecimiento: %s
                - Nombres completos: %s
                ---------------------------------------------------
                """, av.id(), av.nacimiento(), av.fallece(), av.nombre()));
    }

    public void listarLibrosPoridioma() {
        System.out.println("*> Ingresa el idioma (Ejemplo: en, es, fr, etc): ");
        var idioma = lector.nextLine();
        this.repositorioLibro.findByLanguagesContaining(idioma).stream().map(lm -> new LibroDTO(lm.getId(), lm.getTitle(), lm.getLanguages(), lm.getAuthors().stream().map(am -> new AutorDTO(am.getId(), am.getnacimiento(), am.getfallece(), am.getnombre())).collect(Collectors.toList()))).forEach(l -> System.out.printf("""
                --------------------------------------------------
                - Libro                                          -
                --------------------------------------------------
                - Id: %s
                - Título: %s
                - Autores: %s
                - Idiomas: %s
                ---------------------------------------------------
                """, l.id(), l.title(), l.authors().stream().map(a -> a.nombre()).collect(Collectors.joining(",")), String.join(",", l.languages())));
    }

}

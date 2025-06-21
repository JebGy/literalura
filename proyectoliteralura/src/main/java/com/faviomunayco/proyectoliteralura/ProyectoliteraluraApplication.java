package com.faviomunayco.proyectoliteralura;

import com.faviomunayco.proyectoliteralura.repository.RepositorioAutor;
import com.faviomunayco.proyectoliteralura.repository.RepositorioLibro;
import com.faviomunayco.proyectoliteralura.services.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoliteraluraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoliteraluraApplication.class, args);
    }

    @Autowired
    private RepositorioLibro repositorioLibro;
    @Autowired
    private RepositorioAutor repositorioAutor;

    @Override
    public void run(String... args) throws Exception {
        Menu m = new Menu(repositorioLibro, repositorioAutor);
        m.showMenu();
    }
}

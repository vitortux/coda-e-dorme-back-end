package br.com.codaedorme.pi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.codaedorme.pi.infra.view.Menu;

@Component
@Profile("!test")
public class MenuCommandLineRunner implements CommandLineRunner {

    @Autowired
    private Menu menu;

    @Override
    public void run(String... args) throws Exception {
        menu.inicio();
    }
}

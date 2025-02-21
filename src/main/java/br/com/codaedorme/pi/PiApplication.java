package br.com.codaedorme.pi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.codaedorme.pi.domain.usuario.UsuarioMenu;

@SpringBootApplication
public class PiApplication implements CommandLineRunner {

	private UsuarioMenu menu;

	public static void main(String[] args) {
		SpringApplication.run(PiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.menu();
	}

}

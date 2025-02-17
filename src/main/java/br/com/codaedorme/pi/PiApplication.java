package br.com.codaedorme.pi;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.codaedorme.pi.model.Status;
import br.com.codaedorme.pi.model.Usuario;
import br.com.codaedorme.pi.repository.UsuarioRepository;

@SpringBootApplication
public class PiApplication implements CommandLineRunner {

	private static final Scanner SCANNER = new Scanner(System.in);

	@Autowired
	private UsuarioRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(PiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu();
	}

	private void menu() {
		boolean rodando = true;
		int escolha;
		String menu = "1 - Login\n2 - Cadastrar usuario\n3 - Sair";

		while (rodando) {
			System.out.println(menu);
			escolha = SCANNER.nextInt();

			switch (escolha) {
				case 1:
					System.out.println("------ Login ------");
					break;
				case 2:
					cadastrar();
					break;
				case 3:
					System.out.println("------ Rola dura ------");
					rodando = false;
					break;
				default:
					System.out.println("Essa opcao nao existe");
					break;
			}
		}
	}

	private void cadastrar() {
		System.out.println("------ Cadastro ------\n2");
		Usuario usuario = new Usuario();

		System.out.println("Digite o Nome do usuario:");
		usuario.setNome(SCANNER.next());

		System.out.println("Digite o Email do usuario:");
		usuario.setEmail(SCANNER.next());

		System.out.println("Digite o Cpf do usuario:");
		usuario.setCpf(SCANNER.next());

		System.out.println("Digite o grupo do usuario:");
		usuario.setGrupo(SCANNER.next());

		System.out.println("Digite o senha do usuario:");
		usuario.setSenha(SCANNER.next());

		usuario.setStatus(Status.ATIVO);

		repository.save(usuario);
	}
}

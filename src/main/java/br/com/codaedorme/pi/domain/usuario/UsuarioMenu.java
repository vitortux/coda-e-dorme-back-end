package br.com.codaedorme.pi.domain.usuario;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.codaedorme.pi.domain.usuario.enums.Grupo;
import br.com.codaedorme.pi.domain.usuario.enums.Status;

@Component
public class UsuarioMenu {
	private static final Scanner SCANNER = new Scanner(System.in);

	@Autowired
	private UsuarioService service;

	public void menu() {
		boolean rodando = true;
		int escolha;
		String menu = "1 - Login\n2 - Cadastrar usuario\n3 - Listar Usuários\n4 - Sair";

		while (rodando) {
			System.out.println(menu);
			escolha = SCANNER.nextInt();
			SCANNER.nextLine();

			switch (escolha) {
				case 1:
					System.out.println("------ Login ------");
					break;
				case 2:
					cadastrar();
					break;
				case 3:
					listarUsuarios();
					break;
				case 4:
					System.out.println("------ Tchau até mais ------");
					rodando = false;
					break;
				default:
					System.out.println("Essa opcao nao existe");
					break;
			}
		}
	}

	private void cadastrar() {
		System.out.println("------ Cadastro ------\n");
		Usuario usuario = new Usuario();

		System.out.println("Digite o Nome do usuario:");
		usuario.setNome(SCANNER.nextLine());

		boolean emailValido = false;
		while (!emailValido) {
			try {
				System.out.println("Digite o Email do usuario:");
				String email = SCANNER.nextLine();

				if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
					throw new IllegalArgumentException("Email inválido.");
				}
				usuario.setEmail(email);
				emailValido = true;
			} catch (IllegalArgumentException e) {
				System.err.println("Erro de validação: " + e.getMessage());
				System.out.println("Por favor, insira um email válido.");
			}
		}

		boolean cpfValido = false;
		while (!cpfValido) {
			try {
				System.out.println("Digite o Cpf do usuario:");
				String cpf = SCANNER.nextLine();

				if (!cpf.matches("^\\d{11}$")) {
					throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos.");
				}
				usuario.setCpf(cpf);
				cpfValido = true;
			} catch (IllegalArgumentException e) {
				System.err.println("Erro de validação: " + e.getMessage());
				System.out.println("Por favor, insira um CPF válido.");
			}
		}

		System.out.println("Digite o grupo do usuario (ADMINISTRADOR, ESTOQUISTA):");
		String grupoInput = SCANNER.nextLine().toUpperCase();
		Grupo grupo = Grupo.valueOf(grupoInput);
		usuario.setGrupo(grupo);

		System.out.println("Digite a senha do usuario:");
		usuario.setSenha(SCANNER.nextLine());

		System.out.println("Digite novamente a senha do usuario:");
		String senha2 = SCANNER.nextLine();

		usuario.setStatus(Status.ATIVO);

		// Salva o usuário
		service.save(usuario, senha2);

	}

	private void listarUsuarios() {
		System.out.println("------ Lista de Usuários ------");

		Usuario[] usuarios = service.findAll();

		if (usuarios.length == 0) {
			System.out.println("Nenhum usuário cadastrado.");
		} else {
			for (Usuario usuario : usuarios) {
				System.out.println(usuario.toString2());
			}
		}
		opcoesListar();
	}

	private void opcoesListar() {
		while (true) {
			System.out.println("\n1 - Adicionar usuário\n2 - Selecionar usuário\n0 - Voltar para o inicio");

			int opcao = SCANNER.nextInt();
			SCANNER.nextLine();

			switch (opcao) {
				case 1:
					cadastrar();
					break;
				case 2:
					// opcoesUsuario();
					break;
				case 0:
					menu();
					break;
				default:
					System.out.println("Essa opção não existe");
					break;
			}
		}
	}
}

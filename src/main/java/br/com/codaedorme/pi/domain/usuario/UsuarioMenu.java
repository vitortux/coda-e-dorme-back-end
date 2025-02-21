package br.com.codaedorme.pi.domain.usuario;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.codaedorme.pi.domain.usuario.enums.Grupo;
import br.com.codaedorme.pi.domain.usuario.enums.Status;

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

		System.out.println("Digite o Email do usuario:");
		usuario.setEmail(SCANNER.nextLine());

		System.out.println("Digite o Cpf do usuario:");
		usuario.setCpf(SCANNER.nextLine());

		System.out.println("Digite o grupo do usuario (ADMINISTRADOR, ESTOQUISTA):");
		String grupoInput = SCANNER.nextLine().toUpperCase();
		Grupo grupo = Grupo.valueOf(grupoInput);
		usuario.setGrupo(grupo);

		System.out.println("Digite o senha do usuario:");
		usuario.setSenha(SCANNER.nextLine());

		System.out.println("Digite novamente a senha do usuario:");
		String senha2 = SCANNER.nextLine();

		usuario.setStatus(Status.ATIVO);

		System.out.println("Usuário \"" + service.save(usuario, senha2).getNome() + "\" salvo com sucesso.");
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
//				opcoesUsuario();
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

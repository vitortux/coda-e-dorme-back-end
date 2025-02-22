package br.com.codaedorme.pi.domain.usuario;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.com.codaedorme.pi.domain.usuario.enums.Grupo;
import br.com.codaedorme.pi.domain.usuario.enums.Status;
import br.com.codaedorme.pi.infra.validation.ValidaSenha;

@Component
public class UsuarioMenu {
	private static final Scanner SCANNER = new Scanner(System.in);
	@Autowired
	ValidaSenha validador = new ValidaSenha();

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

	private void opcoesAlteracaoUsuario(Long id) {
		System.out.println(
				"1 - Alterar usuário\n2 - Alterar senha \n3 - Ativar/Desativar\n4 - voltar a listar usuário");

		int opcao = SCANNER.nextInt();
		SCANNER.nextLine();

		switch (opcao) {
			case 1:
				alterarUsuario(id);
				break;
			case 2:
				alterarDadoUsuario(id);
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				break;
		}
	}

	private void opcoesListar() {
		System.out.println("\n1 - Adicionar usuário\n2 - Selecionar usuário\n0 - Voltar para o inicio");

		int opcao = Integer.parseInt(SCANNER.nextLine());

		switch (opcao) {
			case 1:
				cadastrar();
				break;
			case 2:
				System.out.println("Digite o id do usuario:");
				Long id = SCANNER.nextLong();
				opcoesAlteracaoUsuario(id);
				break;
			case 0:
				menu();
				break;
			default:
				System.out.println("Essa opção não existe");
				opcoesListar();
				break;
		}
	}

	private void cadastrar() {
		try {
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

			service.save(usuario, senha2);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Email já cadastrado no banco de dados!");
		} catch (IllegalArgumentException e) {
			System.out.println("Digite um dado valido!");
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		}
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

	private void listarUsuarioSelecionado(Long id) {
		Usuario usuario = service.findById(id);
		if (usuario != null) {
			System.out.println(usuario.toString());
		} else {
			System.out.println("Usuario nao encontrado!");
		}
	}

	private void alterarDadoUsuario(Long id) {
		listarUsuarioSelecionado(id);
		Usuario usuario = service.findById(id);

		if (usuario == null) {
			System.out.println("Usuario nao encontrado!");
		}
		System.out.println("Digite a senha antiga:");
		String senhaAntiga = SCANNER.next();

		if (!validador.validaHash(senhaAntiga, usuario.getId())) {
			System.out.println("Senha antiga incorreta!");
			return;
		}

		System.out.println("Senha correta!");

		System.out.println("Digite a nova senha:");
		String senhaNova = SCANNER.next();

		System.out.println("Digite a nova senha novamente:");
		String senhaNova2 = SCANNER.next();

		if (!validador.validaSenhas(senhaNova, senhaNova2)) {
			System.out.println("Senhas não compativeis!");
			return;
		}

		System.out.println("Deseja salvar a senha? (S/N):");
		String confirmacao = SCANNER.next();

		if (confirmacao.equalsIgnoreCase("N")) {
			System.out.println("Senha não alterada!");
			return;
		} else {
			service.alterarSenha(usuario, senhaNova);
			System.out.println("Senha alterada com sucesso!");
		}
	}

	private void alterarUsuario(Long id) {
		try {
			Usuario usuario = service.findById(id);
			usuario.toString();

			System.out.println("Digite o nome do usuario:");
			usuario.setNome(SCANNER.nextLine());

			System.out.println("Digite o CPF do usuario:");
			usuario.setCpf(SCANNER.nextLine());

			System.out.println("Digite o grupo do usuario (ADMINISTRADOR, ESTOQUISTA):");
			String grupoInput = SCANNER.nextLine().toUpperCase();
			Grupo grupo = Grupo.valueOf(grupoInput);
			usuario.setGrupo(grupo);

			System.out.println("Deseja persistir as alterações:? (S/N)");
			String confirmacao = SCANNER.nextLine();

			if (confirmacao.equalsIgnoreCase("S")) {
				service.alter(usuario);
				System.out.println("Alterações salvas com sucesso!");
			} else if (!confirmacao.equalsIgnoreCase("N")) {
				System.out.println("Opção inválida!");
			} else {
				System.out.println("Alterações não salvas!");
			}
		} catch (NullPointerException e) {
			System.out.println("Usuario não encontrado!");
		} catch (DataIntegrityViolationException e) {
			System.out.println("O CPF deve possuir um formato válido!");
		} catch (IllegalArgumentException e) {
			System.out.println("Digite um dado valido!");
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		}
	}
}

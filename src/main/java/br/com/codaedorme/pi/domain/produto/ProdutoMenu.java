package br.com.codaedorme.pi.domain.produto;

import br.com.codaedorme.pi.domain.usuario.Session;
import br.com.codaedorme.pi.domain.usuario.Usuario;
import br.com.codaedorme.pi.domain.usuario.enums.Grupo;
import br.com.codaedorme.pi.domain.usuario.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Scanner;

public class ProdutoMenu {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private ProdutoService service;

    @Autowired
    private Session session;

    public void listarProdutos() {
        System.out.println("------ Lista de Produtos ------");

        Produto[] produtos = service.findAll();

        if (produtos.length == 0) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto.toString());
            }
        }
        opcoesListar();
    }

    private void cadastrar() {
        if (!isAdministrador()) {
            System.out.println("Apenas ADMs podem cadastrar produtos.");
            return;
        }

        try {
            System.out.println("------ Cadastro ------\n");
            Produto produto = new Produto();

            System.out.println("Insira o nome do produto:");
            produto.setNome(SCANNER.nextLine());

            System.out.println("Insira a avaliação do produto (entre 1 e 5, com incrementos de 0.5):");
            produto.setAvaliacao(SCANNER.nextBigDecimal());
            SCANNER.nextLine();//eu te odeio scanner eu te odeio aaaaaaaaaaaaaa

            System.out.println("Insira a descrição do produto:");
            produto.setDescricao(SCANNER.nextLine());

            System.out.println("Insira o preço do produto:");
            produto.setPreco(SCANNER.nextBigDecimal());
            SCANNER.nextLine();//eu te odeio scanner eu te odeio aaaaaaaaaaaaaa

            System.out.println("Insira a quantidade em estoque do produto:");
            produto.setQuantidadeEstoque(SCANNER.nextInt());

            service.save(produto);

            System.out.println("Produto cadastrado com sucesso!");
            System.out.println(produto);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }


    private void opcoesListar() {

    }

    private boolean isAdministrador(){
        return session.getUsuario().getGrupo() == Grupo.ADMINISTRADOR;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

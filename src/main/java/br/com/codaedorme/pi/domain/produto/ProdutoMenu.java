package br.com.codaedorme.pi.domain.produto;

import br.com.codaedorme.pi.domain.usuario.Session;
import br.com.codaedorme.pi.domain.usuario.enums.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Scanner;
@Component
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

            Produto produtoSalvo = service.save(produto);

            do {
                Imagem imagem = cadastrarImagem(produtoSalvo);
                if (imagem != null) {
                    imagem.setProduto(produtoSalvo);
                    produtoSalvo.getImagens().add(imagem);
                }

                System.out.println("Deseja adicionar mais uma imagem? (S/N)");
            } while (SCANNER.nextLine().trim().equalsIgnoreCase("S"));

            service.save(produtoSalvo);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private Imagem cadastrarImagem(Produto produtoSalvo) {
        Imagem imagem = new Imagem();

        SCANNER.nextLine();//eu te odeio scanner eu te odeio aaaaaaaaaaaaaa
        System.out.println("Digite o nome do arquivo da imagem:");
        String nomeArquivo = SCANNER.nextLine();
        imagem.setNome(nomeArquivo);

        System.out.println("Digite o caminho completo da imagem de origem:");
        Path origem = Path.of(SCANNER.nextLine());

        String extensao = "";
        String nomeOriginal = origem.getFileName().toString();
        int pontoIndex = nomeOriginal.lastIndexOf(".");
        if (pontoIndex != -1) {
            extensao = nomeOriginal.substring(pontoIndex);
        }

        Path destinoDiretorio = Path.of("src/main/resources/imagens/" + produtoSalvo.getId());
        try {
            Files.createDirectories(destinoDiretorio);

            Path destinoArquivo = destinoDiretorio.resolve(nomeArquivo + extensao);

            Files.copy(origem, destinoArquivo, StandardCopyOption.REPLACE_EXISTING);
            imagem.setDiretorioDestino(destinoArquivo.toString());

        } catch (IOException e) {
            System.out.println("Erro ao mover a imagem: " + e.getMessage());
            return null;
        }

        System.out.println("Esta é a imagem principal? (S/N)");
        imagem.setImagemPrincipal(SCANNER.nextLine().trim().equalsIgnoreCase("S"));

        if(imagem.getImagemPrincipal()){
            for (Imagem img : produtoSalvo.getImagens()) {
                img.setImagemPrincipal(false);
            }
        }

        return imagem;
    }

    private void opcoesListar() {
        System.out.println("\nI - Incluir produto\nID - Editar/Ativar/Desativar produto\n0 - Voltar para o inicio");
        String opcao = SCANNER.nextLine();

        if(opcao.equalsIgnoreCase("i")){
            cadastrar();
            return;
        }

        if(opcao.equals("0")){
            System.out.println("Voltando ao menu...");
            return;
        }

        if (isNumeric(opcao)) {
            Long id = Long.parseLong(opcao);
            //opcoesAlteracaoProduto(id);
            return;
        }

        System.out.println("Opção inválida.");
        opcoesListar();
    }

    private boolean isAdministrador(){
        return session.getUsuario().getGrupo() == Grupo.ADMINISTRADOR;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

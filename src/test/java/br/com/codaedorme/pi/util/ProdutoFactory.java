package br.com.codaedorme.pi.util;

import java.math.BigDecimal;

import br.com.codaedorme.pi.domain.cli.produto.Imagem;
import br.com.codaedorme.pi.domain.cli.produto.Produto;
import br.com.codaedorme.pi.domain.cli.produto.enums.Status;

public class ProdutoFactory {

    public static Produto criarProdutoComImagemLocal() {
        Produto produto = new Produto();
        produto.setNome("macaco.jpg");
        produto.setDescricao("Travesseiro ortopédico com espuma de memória.");
        produto.setPreco(new BigDecimal("199.90"));
        produto.setQuantidadeEstoque(50);
        produto.setAvaliacao(new BigDecimal("4.5"));
        produto.setStatus(Status.ATIVO);

        Imagem imagem = new Imagem();
        String path = "/home/vitor/Downloads/gato.png";
        imagem.setDiretorioDestino(path);
        imagem.setNome(path.substring(path.lastIndexOf("/") + 1));
        imagem.setImagemPrincipal(true);
        imagem.setProduto(produto);

        produto.addImagem(imagem);

        return produto;
    }
}
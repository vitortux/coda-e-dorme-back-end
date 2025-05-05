package br.com.codaedorme.pi.domain.api.pedido;

import br.com.codaedorme.pi.domain.cli.produto.Produto;

public record ItemPedidoDTO(Produto produto, int qtdProduto, double valorUnitario, double valorSubTotal) {

}

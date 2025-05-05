package br.com.codaedorme.pi.domain.api.pedido;

import java.util.List;

import br.com.codaedorme.pi.domain.api.endereco.Endereco;

public record PedidoDTO(Endereco endereco, List<ItemPedidoDTO> itensPedido, String dataPedido, double valorFrete,
        FormaDePagamento formaDePagamento, double valorTotalPedido, boolean statusPedido) {

}

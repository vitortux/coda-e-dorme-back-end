package br.com.codaedorme.pi.domain.api.pedido;

import java.time.LocalDateTime;
import java.util.List;

import br.com.codaedorme.pi.domain.api.endereco.Endereco;

public record PedidoDTO(Long id, Endereco endereco, List<ItemPedidoDTO> itensPedido, LocalDateTime dataPedido,
        double valorFrete,
        FormaDePagamento formaDePagamento, double valorTotalPedido, StatusPedido statusPedido) {

}

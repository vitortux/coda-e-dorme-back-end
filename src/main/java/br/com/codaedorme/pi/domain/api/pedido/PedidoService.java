package br.com.codaedorme.pi.domain.api.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.api.endereco.Endereco;
import br.com.codaedorme.pi.domain.api.endereco.EnderecoRepository;
import br.com.codaedorme.pi.domain.cli.produto.Produto;
import br.com.codaedorme.pi.domain.cli.produto.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Construtor com injeção de dependências
    public PedidoService(PedidoRepository pedidoRepository, ItemRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public void addPedido(Pedido pedido, List<ItemPedido> itensPedido) {
        for (ItemPedido item : itensPedido) {
            itemPedidoRepository.save(item);
        }
        pedidoRepository.save(pedido);
    }

    public void deletarPedido(Long id) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRepository.delete(pedidoExistente);
    }

    public List<Pedido> listarPedidosPorUsuarioId(Long usuarioId) {
        return pedidoRepository.findAllByIdCliente(usuarioId);
    }

    public List<PedidoDTO> listarPedidosDTOPorUsuarioId(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findAllByIdCliente(usuarioId);

        return pedidos.stream()
                .map(pedido -> {
                    Endereco endereco = enderecoRepository.findById(pedido.getIdEndereco()).get();

                    List<ItemPedidoDTO> itensDTO = pedido.getItensPedido().stream()
                            .map(item -> {
                                Produto produto = produtoRepository.findById(item.getIdProduto()).get();
                                return new ItemPedidoDTO(
                                        produto,
                                        item.getQtdProduto(),
                                        item.getValorUnitario(),
                                        item.getValorSubTotal());
                            })
                            .collect(Collectors.toList());

                    return new PedidoDTO(
                            endereco,
                            itensDTO,
                            pedido.getDataPedido(),
                            pedido.getValorFrete(),
                            pedido.getFormaDePagamento(),
                            pedido.getValorTotalPedido(),
                            pedido.isStatusPedido());
                })
                .collect(Collectors.toList());
    }

}

package br.com.codaedorme.pi.domain.api.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.api.cliente.ClienteRepository;
import br.com.codaedorme.pi.domain.api.endereco.Endereco;
import br.com.codaedorme.pi.domain.api.endereco.EnderecoRepository;
import br.com.codaedorme.pi.domain.cli.produto.Produto;
import br.com.codaedorme.pi.domain.cli.produto.ProdutoRepository;
import br.com.codaedorme.pi.domain.cli.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
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

        @Autowired
        private ClienteRepository clienteRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        // Construtor com injeção de dependências
        public PedidoService(PedidoRepository pedidoRepository, ItemRepository itemPedidoRepository) {
                this.pedidoRepository = pedidoRepository;
                this.itemPedidoRepository = itemPedidoRepository;
        }

        @Transactional
        public Pedido addPedido(Pedido pedido, List<ItemPedido> itensPedido) {
                for (ItemPedido item : itensPedido) {
                        itemPedidoRepository.save(item);
                }
                return pedidoRepository.save(pedido);
        }

        public void deletarPedido(Long id) {
                Pedido pedidoExistente = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
                pedidoRepository.delete(pedidoExistente);
        }

        public List<Pedido> listarPedidosPorUsuarioId(Long usuarioId) {
                return pedidoRepository.findAllByIdCliente(usuarioId);
        }

        public Pedido alterarStatus(Long pedidoId, StatusPedidoDTO status) {
                Pedido pedido = pedidoRepository.findById(pedidoId)
                                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

                pedido.setStatusPedido(status.statusPedido());
                return pedidoRepository.save(pedido);
        }

        // Methodo num fununcia
        // -------------------------------------------------------------------------------------------------
        public List<PedidoDTO> listarPedidosDTOPorUsuarioId(Long usuarioId) {
                List<Pedido> pedidos = pedidoRepository.findAllByIdCliente(usuarioId);

                return pedidos.stream()
                                .map(pedido -> {
                                        Endereco endereco = enderecoRepository.findById(pedido.getIdEndereco()).get();

                                        List<ItemPedidoDTO> itensDTO = pedido.getItensPedido().stream()
                                                        .map(item -> {
                                                                Produto produto = produtoRepository
                                                                                .findById(item.getIdProduto()).get();
                                                                return new ItemPedidoDTO(
                                                                                item.getIdItemPedido(),
                                                                                produto,
                                                                                item.getQtdProduto(),
                                                                                item.getValorUnitario(),
                                                                                item.getValorSubTotal());
                                                        })
                                                        .collect(Collectors.toList());

                                        return new PedidoDTO(
                                                        pedido.getId(),
                                                        endereco,
                                                        itensDTO,
                                                        pedido.getDataPedido(),
                                                        pedido.getValorFrete(),
                                                        pedido.getFormaDePagamento(),
                                                        pedido.getValorTotalPedido(),
                                                        pedido.getStatusPedido());
                                })
                                .collect(Collectors.toList());
        }
        // -------------------------------------------------------------------------------------------------

        public PedidoDTO detalhesDoPedidoPorUsuario(Long pedidoId) {
                Pedido pedido = pedidoRepository.findById(pedidoId)
                                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

                Endereco endereco = enderecoRepository.findById(pedido.getIdEndereco())
                                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

                List<ItemPedidoDTO> itensDTO = pedido.getItensPedido().stream()
                                .map(item -> {
                                        Produto produto = produtoRepository.findById(item.getIdProduto())
                                                        .orElseThrow(() -> new EntityNotFoundException(
                                                                        "Produto não encontrado"));
                                        return new ItemPedidoDTO(
                                                        item.getIdItemPedido(),
                                                        produto,
                                                        item.getQtdProduto(),
                                                        item.getValorUnitario(),
                                                        item.getValorSubTotal());
                                })
                                .collect(Collectors.toList());
                return new PedidoDTO(
                                pedido.getId(),
                                endereco,
                                itensDTO,
                                pedido.getDataPedido(),
                                pedido.getValorFrete(),
                                pedido.getFormaDePagamento(),
                                pedido.getValorTotalPedido(),
                                pedido.getStatusPedido());
        }

        public List<PedidoDTO> listarTodosPedidosParaEstoquista() {

                List<Pedido> pedidos = pedidoRepository.findAll();

                return pedidos.stream().map(pedido -> {
                        Endereco endereco = enderecoRepository.findById(pedido.getIdEndereco())
                                        .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

                        List<ItemPedidoDTO> itensDTO = pedido.getItensPedido().stream().map(item -> {
                                Produto produto = produtoRepository.findById(item.getIdProduto())
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                                "Produto não encontrado"));

                                return new ItemPedidoDTO(
                                                item.getIdItemPedido(),
                                                produto,
                                                item.getQtdProduto(),
                                                item.getValorUnitario(),
                                                item.getValorSubTotal());
                        }).collect(Collectors.toList());

                        return new PedidoDTO(
                                        pedido.getId(),
                                        endereco,
                                        itensDTO,
                                        pedido.getDataPedido(),
                                        pedido.getValorFrete(),
                                        pedido.getFormaDePagamento(),
                                        pedido.getValorTotalPedido(),
                                        pedido.getStatusPedido());
                }).collect(Collectors.toList());
        }

}

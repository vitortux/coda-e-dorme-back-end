package br.com.codaedorme.pi.domain.api.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemRepository itemPedidoRepository;

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

    public List<Pedido> listarTodosOsPedidos() {
        return pedidoRepository.findAll();
    }

}

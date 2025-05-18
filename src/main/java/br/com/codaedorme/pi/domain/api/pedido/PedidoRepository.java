package br.com.codaedorme.pi.domain.api.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    public List<Pedido> findAllByIdCliente(Long id);
}
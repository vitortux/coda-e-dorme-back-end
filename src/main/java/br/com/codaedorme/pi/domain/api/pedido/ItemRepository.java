package br.com.codaedorme.pi.domain.api.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPedido, Long> {

}
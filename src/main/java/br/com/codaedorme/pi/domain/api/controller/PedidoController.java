package br.com.codaedorme.pi.domain.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.pedido.ItemPedido;
import br.com.codaedorme.pi.domain.api.pedido.Pedido;
import br.com.codaedorme.pi.domain.api.pedido.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping("/addPedido")
    public ResponseEntity<?> addPedido(@RequestBody Pedido pedido) {
        try {
            List<ItemPedido> itensPedido = pedido.getItensPedido();

            service.addPedido(pedido, itensPedido);

            return ResponseEntity.ok("Pedido adicionado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar pedido: " + e.getMessage());
        }
    }

    @GetMapping("/buscarPedidos")
    public ResponseEntity<?> buscarPedidos() {
        try {
            return ResponseEntity.ok(service.listarTodosOsPedidos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar pedidos: " + e.getMessage());
        }
    }

}

package br.com.codaedorme.pi.domain.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteService;
import br.com.codaedorme.pi.domain.api.pedido.ItemPedido;
import br.com.codaedorme.pi.domain.api.pedido.Pedido;
import br.com.codaedorme.pi.domain.api.pedido.PedidoService;
import br.com.codaedorme.pi.domain.api.security.TokenService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PedidoService service;

    @Autowired
    private ClienteService servicec;

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

    @GetMapping("/me")
    public ResponseEntity<Cliente> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        Cliente cliente = (Cliente) servicec.findByEmail(email);
        return ResponseEntity.ok((cliente));
    }

    @GetMapping("/buscarPedidos")
    public ResponseEntity<?> buscarPedidos(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        Cliente cliente = (Cliente) servicec.findByEmail(email);
        return ResponseEntity.ok(service.listarPedidosPorUsuarioId(cliente.getId()));
    }
}

package br.com.codaedorme.pi.domain.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteService;
import br.com.codaedorme.pi.domain.api.pedido.ItemPedido;
import br.com.codaedorme.pi.domain.api.pedido.Pedido;
import br.com.codaedorme.pi.domain.api.pedido.PedidoDTO;
import br.com.codaedorme.pi.domain.api.pedido.PedidoService;
import br.com.codaedorme.pi.domain.api.pedido.StatusPedidoDTO;
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
            Pedido pedidoSalvo = service.addPedido(pedido, itensPedido);

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Pedido adicionado com sucesso.");
            resposta.put("idPedido", pedidoSalvo.getId());
            resposta.put("valorTotal", pedidoSalvo.getValorTotalPedido());

            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao adicionar pedido: " + e.getMessage()));
        }
    }

    @GetMapping("/detalhesPedido/{pedidoId}")
    public ResponseEntity<?> detalhesDoPedido(@RequestHeader("Authorization") String authHeader,
            @PathVariable Long pedidoId) {

        PedidoDTO pedidoDTO = service.detalhesDoPedidoPorUsuario(pedidoId);
        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<?> listarPedidos(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(service.listarTodosPedidosParaEstoquista());
    }

    @PutMapping("/alterarStatusPedido/{id}")
    public ResponseEntity<?> alterarStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long pedidoId,
            @RequestBody StatusPedidoDTO statusPedidoDTO) {

        Pedido pedidoAtualizado = service.alterarStatus(pedidoId, statusPedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @GetMapping("/buscarPedidos")
    public ResponseEntity<?> buscarPedidos(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        Cliente cliente = (Cliente) servicec.findByEmail(email);
        return ResponseEntity.ok(service.listarPedidosDTOPorUsuarioId(cliente.getId()));
    }
}

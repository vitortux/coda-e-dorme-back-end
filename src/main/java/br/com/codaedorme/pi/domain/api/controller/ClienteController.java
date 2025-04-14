package br.com.codaedorme.pi.domain.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteService;
import br.com.codaedorme.pi.domain.api.cliente.ClienteUpdateDTO;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/profile")
    public UserDetails getCliente(@RequestParam String email) {
        return clienteService.findByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id,
            @RequestBody ClienteUpdateDTO clienteDTO) {
        try {
            Cliente clienteAtualizado = clienteService.updateCliente(id, clienteDTO);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/endereco-padrao/{enderecoId}")
    public ResponseEntity<Void> atualizarEnderecoPadrao(
            @PathVariable Long id,
            @PathVariable Long enderecoId) {
        clienteService.definirEnderecoPadrao(id, enderecoId);
        return ResponseEntity.ok().build();
    }
}

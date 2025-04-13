package br.com.codaedorme.pi.domain.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<Cliente> cadastro(@RequestBody Cliente cliente) {
        Cliente addCliente = this.clienteService.save(cliente);
        return new ResponseEntity<>(addCliente, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public String getMethodName() {
        return new String("oi");
    }

}

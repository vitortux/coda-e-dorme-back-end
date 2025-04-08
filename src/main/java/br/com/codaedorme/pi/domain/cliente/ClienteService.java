package br.com.codaedorme.pi.domain.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

}

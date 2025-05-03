package br.com.codaedorme.pi.domain.api.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.api.endereco.Endereco;
import br.com.codaedorme.pi.domain.api.endereco.EnderecoDTO;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Cliente updateCliente(Long id, ClienteUpdateDTO clienteDTO) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (clienteDTO.nomeCompleto() != null) {
            clienteExistente.setNomeCompleto(clienteDTO.nomeCompleto());
        }

        if (clienteDTO.dataNascimento() != null) {
            clienteExistente.setDataNascimento(clienteDTO.dataNascimento());
        }

        if (clienteDTO.genero() != null) {
            clienteExistente.setGenero(clienteDTO.genero());
        }

        if (clienteDTO.novaSenha() != null && !clienteDTO.novaSenha().isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(clienteDTO.novaSenha());
            clienteExistente.setSenha(senhaCriptografada);
        }

        if (clienteDTO.novosEnderecosEntrega() != null && !clienteDTO.novosEnderecosEntrega().isEmpty()) {
            for (EnderecoDTO enderecoDTO : clienteDTO.novosEnderecosEntrega()) {
                Endereco endereco = new Endereco();
                endereco.setLogradouro(enderecoDTO.logradouro());
                endereco.setNumero(enderecoDTO.numero());
                endereco.setComplemento(enderecoDTO.complemento());
                endereco.setBairro(enderecoDTO.bairro());
                endereco.setCidade(enderecoDTO.cidade());
                endereco.setEstado(enderecoDTO.estado());
                endereco.setCep(enderecoDTO.cep());
                endereco.setCliente(clienteExistente);
                clienteExistente.getEnderecoEntrega().add(endereco);
            }
        }

        return repository.save(clienteExistente);
    }

    public void definirEnderecoPadrao(Long clienteId, Long enderecoId) {
        Cliente cliente = repository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.getEnderecoEntrega().forEach(e -> e.setPadrao(false));

        cliente.getEnderecoEntrega().stream()
                .filter(e -> e.getId().equals(enderecoId))
                .findFirst()
                .ifPresent(e -> e.setPadrao(true));

        repository.save(cliente);
    }

    public void addEndereco(Cliente cliente, Endereco endereco) {
        endereco.setCliente(cliente);

        cliente.getEnderecoEntrega().add(endereco);

        repository.save(cliente);
    }
}

package br.com.codaedorme.pi.domain.api.cliente;

import java.time.LocalDate;
import java.util.List;

import br.com.codaedorme.pi.domain.api.endereco.EnderecoDTO;

public record ClienteUpdateDTO(
        String nomeCompleto,
        LocalDate dataNascimento,
        String genero,
        String novaSenha,
        List<EnderecoDTO> novosEnderecosEntrega) {
}

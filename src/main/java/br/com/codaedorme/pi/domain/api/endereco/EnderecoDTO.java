package br.com.codaedorme.pi.domain.api.endereco;

public record EnderecoDTO(
                Long id,
                String logradouro,
                String numero,
                String complemento,
                String bairro,
                String cidade,
                String estado,
                String cep,
                Boolean padrao) {
}

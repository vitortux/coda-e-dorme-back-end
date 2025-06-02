package br.com.codaedorme.pi.util;

public enum ClienteFactory {
  CLIENTE_PADRAO;

  public String getJson() {
    return """
        {
          "email": "pero@teste.com",
          "senha": "123456",
          "cpf": "52998224725",
          "nomeCompleto": "Joo da Silva",
          "dataNascimento": "2000-05-01",
          "genero": "Masculino",
          "role": "USER",
          "enderecoFaturamento": {
            "logradouro": "Rua Faturamento",
            "numero": "100",
            "complemento": "Ap 10",
            "bairro": "Centro",
            "cidade": "São Paulo",
            "estado": "SP",
            "cep": "12345678",
            "padrao": true
          },
          "enderecoEntrega": [
            {
              "logradouro": "Rua Entrega 1",
              "numero": "200",
              "complemento": "Casa",
              "bairro": "Bairro 1",
              "cidade": "São Paulo",
              "estado": "SP",
              "cep": "87654321",
              "padrao": true
            },
            {
              "logradouro": "Rua Entrega 2",
              "numero": "300",
              "complemento": "Bloco B",
              "bairro": "Bairro 2",
              "cidade": "São Paulo",
              "estado": "SP",
              "cep": "11223344",
              "padrao": false
            }
          ]
        }
        """;
  }
}

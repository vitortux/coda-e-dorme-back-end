package br.com.codaedorme.pi;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class AuthenticationLoginLiveTest {

  private final String URL_LOGIN = "http://localhost:8080/auth/login";
  private final String URL_REGISTER = "http://localhost:8080/auth/register";

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  public void deveRegistrarClienteComSucessoEEncontrarNoPerfil() {
    // 1) Monta o JSON de cadastro
    String novoClienteJson = """
        {
                          "email": "piroca1mole@teste.com",
                          "senha": "senha123",
                          "cpf": "11140328932",
                          "nomeCompleto": "Usuário Teste",
                          "dataNascimento": "1990-12-31",
                          "genero": "Masculino",
                          "role": "USER",
                          "enderecoFaturamento": {
                            "logradouro": "Rua Exemplo",
                            "numero": "10",
                            "complemento": "Sala 1",
                            "bairro": "Centro",
                            "cidade": "São Paulo",
                            "estado": "SP",
                            "cep": "01001000",
                            "padrao": true
                          },
                          "enderecoEntrega": [
                            {
                              "logradouro": "Rua Entrega",
                              "numero": "20",
                              "complemento": "",
                              "bairro": "Bairro A",
                              "cidade": "São Paulo",
                              "estado": "SP",
                              "cep": "02002000",
                              "padrao": true
                            }
                          ]
                        }
                        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> registerRequest = new HttpEntity<>(novoClienteJson, headers);
    ResponseEntity<Void> registerResponse = restTemplate.postForEntity(URL_REGISTER, registerRequest, Void.class);

    assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void deveRejeitarCadastroPeloCpfJaCadastrado() {
    String novoClienteJson = """
        {
          "email": "PINTO@teste.com",
          "senha": "senha123",
          "cpf": "12345678903",
          "nomeCompleto": "Usuário Teste",
          "dataNascimento": "1990-12-31",
          "genero": "Masculino",
          "role": "USER",
          "enderecoFaturamento": {
            "logradouro": "Rua Exemplo",
            "numero": "10",
            "complemento": "Sala 1",
            "bairro": "Centro",
            "cidade": "São Paulo",
            "estado": "SP",
            "cep": "01001000",
            "padrao": true
          },
          "enderecoEntrega": [
            {
              "logradouro": "Rua Entrega",
              "numero": "20",
              "complemento": "",
              "bairro": "Bairro A",
              "cidade": "São Paulo",
              "estado": "SP",
              "cep": "02002000",
              "padrao": true
            }
          ]
        }
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> registerRequest = new HttpEntity<>(novoClienteJson, headers);

    RestTemplate customRestTemplate = new RestTemplate();
    customRestTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
      }
    });
    ResponseEntity<Void> registerResponse = customRestTemplate.postForEntity(URL_REGISTER, registerRequest,
        Void.class);

    assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void deveAutenticarComCredenciaisValidasERetornarToken() {
    String loginJson = """
            {
                "email": "admin@admin",
                "senha": "admin123"
            }
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(loginJson, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(URL_LOGIN, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("token");
  }

  @Test
  public void deveRetornar403QuandoSenhaIncorreta() {
    String loginJson = """
            {
                "email": "admin@admin",
                "senha": "admin12"
            }
        """;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(loginJson, headers);

    RestTemplate customRestTemplate = new RestTemplate();
    customRestTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
      }
    });

    ResponseEntity<String> response = customRestTemplate.postForEntity(URL_LOGIN, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }
}

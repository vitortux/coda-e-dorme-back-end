package br.com.codaedorme.pi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.codaedorme.pi.domain.api.cliente.AuthenticationDTO;
import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteRepository;
import br.com.codaedorme.pi.util.ClienteFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthenticationControllerTest {

  @Autowired
  private TestRestTemplate rest;

  @Autowired
  private ClienteRepository repository;

  @LocalServerPort
  private int port;

  private String URL_LOGIN() {
    return "http://localhost:" + port + "/auth/login";
  }

  private String URL_REGISTER() {
    return "http://localhost:" + port + "/auth/register";
  }

  @Test
  void deveRegistrarClienteComSucessoEEncontrarNoPerfil() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(ClienteFactory.CLIENTE_PADRAO.getJson(), headers);

    ResponseEntity<?> cadastro = rest.postForEntity(URL_REGISTER(), request, Cliente.class);
    assertEquals(HttpStatus.OK, cadastro.getStatusCode());

    AuthenticationDTO auth = new AuthenticationDTO("pero@teste.com",
        "123456");
    ResponseEntity<String> response = rest.postForEntity(URL_LOGIN(), auth, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    assertTrue(response.getBody().contains("token"));
    assertTrue(response.getBody().contains("cliente"));

    Optional<Cliente> cliente = repository.findById(1L);
    assertTrue(cliente.isPresent());
  }

  @Test
  void naoDeveCadastrarClienteComCpfRepetido() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(ClienteFactory.CLIENTE_PADRAO.getJson(), headers);

    ResponseEntity<?> cadastro = rest.postForEntity(URL_REGISTER(), request, Cliente.class);
    assertEquals(HttpStatus.BAD_REQUEST, cadastro.getStatusCode());

    Optional<Cliente> cliente = repository.findById(2L);
    assertTrue(cliente.isEmpty());
  }

  @Test
  void naoDeveRealizarLoginComSenhaIncorreta() {
    AuthenticationDTO authIncorreto = new AuthenticationDTO("pero@teste.com", "senhaErrada123");
    ResponseEntity<String> response = rest.postForEntity(URL_LOGIN(), authIncorreto, String.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertNull(response.getBody());
  }
}

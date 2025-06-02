package br.com.codaedorme.pi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import br.com.codaedorme.pi.domain.cli.produto.Produto;
import br.com.codaedorme.pi.domain.cli.produto.ProdutoService;
import br.com.codaedorme.pi.domain.cli.produto.enums.Status;
import br.com.codaedorme.pi.util.ProdutoFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoServiceTest {

    @Autowired
    private ProdutoService service;

    private final Produto produto = ProdutoFactory.criarProdutoComImagemLocal();

    @Test
    @Order(1)
    void deveCadastrarProdutoComSucesso() {
        Produto salvo = service.save(produto);

        assertEquals(produto.getNome(), salvo.getNome());
        assertEquals(1, salvo.getImagens().size());
    }

    @Test
    @Order(2)
    void deveBuscarProdutoPeloId() {
        Produto recuperado = service.findById(1L);

        assertNotNull(recuperado);
        assertEquals("macaco.jpg", recuperado.getNome());
    }

    @Test
    @Order(3)
    void deveBuscarUsuarioPeloIDERetornarNaoEncontrado() {
        Long idInexistente = 124124L;
        Produto resultado = service.findById(idInexistente);

        assertNull(resultado);
    }

    @Test
    @Order(4)
    void deveRetornarTodosOsProdutos() {
        Produto novo = ProdutoFactory.criarProdutoComImagemLocal();
        novo.setNome("Outro produto");

        service.save(novo);

        Produto[] produtos = service.findAll();
        assertEquals(2, produtos.length);

        assertEquals("macaco.jpg", produtos[0].getNome());
        assertEquals("Outro produto", produtos[1].getNome());
    }

    @Test
    @Order(5)
    void deveAlternarStatusDeAtivoParaInativo() {
        Produto recuperado = service.findById(1L);

        service.alterarStatus(recuperado);

        assertEquals(Status.INATIVO, recuperado.getStatus());
    }

    @Test
    @Order(6)
    void deveAlternarStatusDeInativoParaAtivo() {
        Produto recuperado = service.findById(1L);

        service.alterarStatus(recuperado);

        assertEquals(Status.ATIVO, recuperado.getStatus());
    }

    @Test
    @Order(7)
    void deveBuscarProdutoComImagens() {
        Produto resultado = service.findByIdComImagens(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertNotNull(resultado.getImagens());
        assertFalse(resultado.getImagens().isEmpty());
    }

    @Test
    @Order(8)
    void deveRetornarNullQuandoProdutoNaoExistir() {
        Produto resultado = service.findByIdComImagens(-1L);
        assertNull(resultado);
    }

    @Test
    @Order(9)
    void deveRetornarProdutosPaginados() {
        for (int i = 0; i < 5; i++) {
            Produto produto = ProdutoFactory.criarProdutoComImagemLocal();
            produto.setNome("Produto " + i);
            service.save(produto);
        }

        Pageable pageable = PageRequest.of(0, 3);

        Page<Produto> page = service.findAll(pageable);

        assertNotNull(page);
        assertEquals(3, page.getContent().size());
        assertEquals(7, page.getTotalElements());
        assertTrue(page.getTotalPages() >= 2);
    }
}

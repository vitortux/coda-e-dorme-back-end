package br.com.codaedorme.pi.domain.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.produto.enums.Status;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Produto findByIdImagem(Long id) {
        Produto produto = repository.findById(id).orElse(null);
        if (produto != null) {
            produto.getImagens().size();
        }
        return produto;
    }

    public Produto[] findAll() {
        return repository.findAll().toArray(new Produto[0]);
    }

    public void alterarStatus(Produto produto) {
        produto.setStatus(produto.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);
        repository.save(produto);
    }
}

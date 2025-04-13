package br.com.codaedorme.pi.domain.cli.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.cli.produto.enums.Status;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    @Transactional
    public Produto findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Produto findByIdComImagens(Long id) {
        return repository.findByIdComImagens(id);
    }

    public Produto[] findAll() {
        return repository.findAll().toArray(new Produto[0]);
    }

    @Transactional
    public Page<Produto> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void alterarStatus(Produto produto) {
        produto.setStatus(produto.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);
        repository.save(produto);
    }
}

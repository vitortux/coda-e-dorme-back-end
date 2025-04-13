package br.com.codaedorme.pi.domain.cli.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findAll(Pageable pageable);

    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.imagens WHERE p.id = :id")
    Produto findByIdComImagens(@Param("id") Long id);
}

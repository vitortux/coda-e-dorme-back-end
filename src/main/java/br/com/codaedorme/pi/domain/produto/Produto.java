package br.com.codaedorme.pi.domain.produto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal avaliacao;

    @Column(length = 2000)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        if (avaliacao.compareTo(BigDecimal.ZERO) < 0 || avaliacao.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("Avaliação deve ser entre 1 e 5.");
        }

        BigDecimal avaliacaoMultiplicada = avaliacao.multiply(BigDecimal.TEN);
        if (avaliacaoMultiplicada.remainder(BigDecimal.valueOf(5)).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Avaliação deve ser entre 1.0 e 5.0, com incrementos de 0.5");
        }
        this.avaliacao = avaliacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return  id +
                " | " + nome +
                " | " + avaliacao +
                " | " + descricao +
                " | " + preco +
                " | " + quantidadeEstoque;
    }
}

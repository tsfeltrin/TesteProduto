package br.com.f1rst.produto.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= "produto")
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public ProdutoModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoModel setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Integer setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this.getQuantidade();
    }
}

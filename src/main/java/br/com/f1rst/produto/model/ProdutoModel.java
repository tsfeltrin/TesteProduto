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

    public void salvar(ProdutoModel produtoModel){

    }

   /* @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProdutoModel that = (ProdutoModel) o;
        return Object.equals(id, that.id) && Object.equals(nome, that.nome) && Object.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, nome, quantidade);
    }*/
}

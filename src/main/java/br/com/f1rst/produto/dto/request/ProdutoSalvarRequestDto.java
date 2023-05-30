package br.com.f1rst.produto.dto.request;

public class ProdutoSalvarRequestDto {

    private Long id;
    private String nome;
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public ProdutoSalvarRequestDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoSalvarRequestDto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoSalvarRequestDto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}

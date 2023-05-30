package br.com.f1rst.produto.dto.response;

public class ProdutoSavarResponseDto {

    private Long id;
    private String nome;
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public ProdutoSavarResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoSavarResponseDto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoSavarResponseDto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}

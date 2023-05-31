package br.com.f1rst.produto;

import br.com.f1rst.produto.model.ProdutoModel;
import br.com.f1rst.produto.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoServiceTeste {

    @Autowired
    private ProdutoService produtoService;

    @Test
    @DisplayName("Test - Inserir produto - OK!")
    void inserir_produto_ok(){

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Produto test 2");
        produtoModel.setQuantidade(0);

        ProdutoModel produtoModelSaved = produtoService.salvar(produtoModel);

        Assertions.assertNotNull(produtoModelSaved.getId());
        Assertions.assertEquals(produtoModel.getNome(), produtoModelSaved.getNome());
        Assertions.assertEquals(produtoModel.getQuantidade(), produtoModelSaved.getQuantidade());

    }

    @Test
    void excluir_produto() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Produto test");
        produtoModel.setQuantidade(0);

        ProdutoModel produtoModelSaved = produtoService.salvar(produtoModel);

        Long idProduto = produtoModelSaved.getId();

        produtoService.excluir(idProduto);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelDeleted = produtoService.getById(idProduto);
        });

        Assertions.assertEquals("ID nao encontrado", exception.getMessage());

    }

    @Test
    void get_id_invalido() {

        Exception exception = Assertions.assertThrows(Exception.class, () -> {

            ProdutoModel produtoModel = produtoService.getById(100);

        });

        Assertions.assertEquals("ID nao encontrado", exception.getMessage());

    }

    @Test
    void quantidade_obrigatorio() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Produto Teste");

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelSaved = produtoService.salvar(produtoModel);
        });

        Assertions.assertEquals("Digite a quantidade do produto.", exception.getMessage());

    }

    @Test
    void nome_obrigatorio() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(null);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelSaved = produtoService.salvar(produtoModel);
        });

        Assertions.assertEquals("Digite um nome.", exception.getMessage());

    }

    @Test
    void get_by_id() throws Exception {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Produto test3");
        produtoModel.setQuantidade(0);

        ProdutoModel produtoModelSaved = produtoService.salvar(produtoModel);

        Long idProduto = produtoModelSaved.getId();

        ProdutoModel produtoModelGet = produtoService.getById(idProduto);

        Assertions.assertEquals(produtoModelSaved.getId(), produtoModelGet.getId());
        Assertions.assertEquals(produtoModelSaved.getNome(), produtoModelGet.getNome());
        Assertions.assertEquals(produtoModelSaved.getQuantidade(), produtoModelGet.getQuantidade());
    }

}

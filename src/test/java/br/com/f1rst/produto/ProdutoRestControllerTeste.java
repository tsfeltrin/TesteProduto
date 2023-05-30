package br.com.f1rst.produto;


import br.com.f1rst.produto.dto.request.ProdutoSalvarRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoRestControllerTeste {

@Autowired
TestRestTemplate restTemplate;

@Test
    void inserir_produto_rest(){
    ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
    requestDto.setNome("produto test");
    requestDto.setQuantidade(0);

    ResponseEntity<ProdutoSalvarResponseDto> responseDto =
            restTemplate.exchange(
                    "/produto",
                    HttpMethod.POST,
                    new HttpEntity<>(requestDto),
                    ProdutoSalvarResponseDto.class);

    Assertions.assertEquals(201, responseDto.getStatusCode().value());
    Assertions.assertNotNull(responseDto.getBody());
    Assertions.assertNotNull(responseDto.getBody().getId());
    Assertions.assertEquals(requestDto.getNome(), responseDto.getBody().getNome());
    Assertions.assertEquals(requestDto.getQuantidade(), responseDto.getBody().getQuantidade());

}

    @Test
    void listar_todos_produtos() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test listar 1");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarRequestDto> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarRequestDto.class);

        Long idProduto1 = responseDto.getBody().getId();

        requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test listar 2");
        requestDto.setQuantidade(0);

        responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto2 = responseDto.getBody().getId();

        ResponseEntity<List<ProdutoResponseDto>> responseGetDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProdutoResponseDto>>() {});

        List<ProdutoResponseDto> produtoList = responseGetDto.getBody();

        Assertions.assertFalse(produtoList.isEmpty());

        boolean existsProduto1 = false;
        boolean existsProduto2 = false;
        for (ProdutoResponseDto produtoResponseDto : produtoList) {
            if (produtoResponseDto.getId().equals(idProduto1)) {
                existsProduto1 = true;
            } else if (produtoResponseDto.getId().equals(idProduto2)) {
                existsProduto2 = true;
            }
        }

        Assertions.assertTrue(existsProduto1);

        Assertions.assertTrue(existsProduto2);

        Assertions.assertTrue(
                produtoList.stream().anyMatch(produtoResponseDto -> produtoResponseDto.getId().equals(idProduto1))
        );

        Assertions.assertTrue(
                produtoList.stream().anyMatch(produtoResponseDto -> produtoResponseDto.getId().equals(idProduto2))
        );

    }

    @Test
    void get_produto_by_id() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produto/" + idProduto,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDto.class);

        ProdutoResponseDto responseBody = responseGetDto.getBody();

        Assertions.assertEquals(200, responseGetDto.getStatusCode().value());
        Assertions.assertEquals(idProduto, responseBody.getId());
        Assertions.assertEquals(requestDto.getNome(), responseBody.getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responseBody.getQuantidade());


    }

    @Test
    void atualizar_produto() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test atualizar");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        requestDto.setNome("nome atualizado");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responsePutDto =
                restTemplate.exchange(
                        "/produto/" + idProduto,
                        HttpMethod.PUT,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);


        Assertions.assertEquals(200, responsePutDto.getStatusCode().value());
        Assertions.assertEquals(idProduto, responsePutDto.getBody().getId());
        Assertions.assertEquals(requestDto.getNome(), responsePutDto.getBody().getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responsePutDto.getBody().getQuantidade());

    }

    @Test
    void excluir_produto() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test excluir");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produto",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<?> responseDeleteDto =
                restTemplate.exchange(
                        "/produto/" + idProduto,
                        HttpMethod.DELETE,
                        null,
                        Object.class
                );

        Assertions.assertEquals(202, responseDeleteDto.getStatusCode().value());

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produto/" + idProduto,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDto.class);

        Assertions.assertEquals(404, responseGetDto.getStatusCode().value());
    }
}

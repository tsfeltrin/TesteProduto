package br.com.f1rst.produto;


import br.com.f1rst.produto.dto.request.ProdutoSalvarRequestDto;
import br.com.f1rst.produto.dto.response.ProdutoResponseDto;
import br.com.f1rst.produto.dto.response.ProdutoSalvarResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
                    "/produtos",
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
    void get_produto_by_id() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("produto test");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
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
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        requestDto.setNome("nome atualizado");
        requestDto.setQuantidade(0);

        ResponseEntity<ProdutoSalvarResponseDto> responsePutDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
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
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<?> responseDeleteDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.DELETE,
                        null,
                        Object.class
                );

        Assertions.assertEquals(202, responseDeleteDto.getStatusCode().value());

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDto.class);

        Assertions.assertEquals(404, responseGetDto.getStatusCode().value());
    }
}

package br.com.f1rst.produto.controller;


import br.com.f1rst.produto.dto.request.ProdutoSalvarRequestDto;
import br.com.f1rst.produto.dto.response.ProdutoResponseDto;
import br.com.f1rst.produto.dto.response.ProdutoSavarResponseDto;
import br.com.f1rst.produto.model.ProdutoModel;
import br.com.f1rst.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoRestController {

    final ProdutoService produtoService;

    public ProdutoRestController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoSalvarRequestDto create(@RequestBody ProdutoSalvarRequestDto requestDto) throws Exception {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(requestDto.getNome());
        produtoModel.setQuantidade(requestDto.getQuantidade());

        produtoModel = produtoService.salva(produtoModel);

        ProdutoSavarResponseDto responseDto = new ProdutoSavarResponseDto();
        responseDto.setId(produtoModel.getId());
        responseDto.setNome(produtoModel.getNome());
        responseDto.setQuantidade(produtoModel.setQuantidade(0));

        return responseDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDto> findAll() {

        List<ProdutoResponseDto> responseDtoList = new ArrayList<>();

        List<ProdutoModel> produtoModelList = produtoService.listarTodos();

        produtoModelList.forEach(produtoModel -> {
            ProdutoResponseDto produtoResponseDto = new ProdutoResponseDto();
            produtoResponseDto.setId(produtoModel.getId());
            produtoResponseDto.setQuantidade(produtoModel.getQuantidade());
            produtoResponseDto.setNome(produtoModel.getNome());
            responseDtoList.add(produtoResponseDto);
        });

        return responseDtoList;

    }

    @GetMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDto getById(@PathVariable Long idProduto) {

        ProdutoModel produtoModel = produtoService.getById(idProduto);

        ProdutoResponseDto produtoResponseDto = new ProdutoResponseDto();
        produtoResponseDto.setId(produtoModel.getId());
        produtoResponseDto.setQuantidade(produtoModel.setQuantidade(0));
        produtoResponseDto.setNome(produtoModel.getNome());
        return produtoResponseDto;

    }

    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoSavarResponseDto update(@PathVariable Long idProduto, @RequestBody ProdutoSavarResponseDto requestDto) {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(idProduto);
        produtoModel.setNome(requestDto.getNome());
        produtoModel.setQuantidade(requestDto.getQuantidade());

        produtoModel = produtoModel.salvar(produtoModel);

        ProdutoSavarResponseDto responseDto = new ProdutoSavarResponseDto();
        responseDto.setId(produtoModel.getId());
        responseDto.setNome(produtoModel.getNome());
        responseDto.setQuantidade(produtoModel.getQuantidade());

        return responseDto;
    }

    @DeleteMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long idProduto) {
        produtoService.excluir(idProduto);
    }

}
package br.com.f1rst.produto.controller;

import br.com.f1rst.produto.dto.request.ProdutoSalvarRequestDto;
import br.com.f1rst.produto.dto.response.ProdutoResponseDto;
import br.com.f1rst.produto.dto.response.ProdutoSavarResponseDto;
import br.com.f1rst.produto.model.ProdutoModel;
import br.com.f1rst.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/produto")
public class ProdutoController {
    final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listaTodos(Model model) {
        List<ProdutoModel> produtos = produtoService.listarTodos();
        produtos.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        model.addAttribute("produtos", produtos);
        return "produto-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new ProdutoSalvarRequestDto());
        return "produto-edit";
    }
   @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDto> findAll(){

        List<ProdutoResponseDto> responseDtoList = new ArrayList<>();

        List<ProdutoModel> produtoModelList = produtoService.listarTodos();

        produtoModelList.forEach(produtoModel ->{
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
    public ProdutoResponseDto update(@PathVariable Long id, @RequestBody ProdutoSalvarRequestDto produtoRequestDto) throws Exception {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setId(id);
        produtoModel.setNome(produtoRequestDto.getNome());
        produtoModel.setQuantidade(produtoRequestDto.getQuantidade());

        produtoModel = produtoService.salva(produtoModel);

        ProdutoResponseDto produtoResponseDto = new ProdutoResponseDto();
        produtoResponseDto.setId(produtoModel.getId());
        produtoResponseDto.setNome(produtoModel.getNome());
        produtoResponseDto.setQuantidade(produtoModel.getQuantidade());

        return produtoResponseDto;
    }

    @PostMapping("/salva")
    public String salva(@ModelAttribute ProdutoSavarResponseDto produtoDto, BindingResult errors) {

        if (errors.hasErrors()) {
            return "produto-edit";
        }

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(produtoDto.getNome());
        produtoModel.setQuantidade(produtoDto.getQuantidade());

        produtoService.salva(produtoDto);

        return "redirect:/produto";
    }


    @DeleteMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long idProduto) {
        produtoService.excluir(idProduto);
    }


}

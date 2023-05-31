package br.com.f1rst.produto.service;

import br.com.f1rst.produto.exception.NotFoundException;
import br.com.f1rst.produto.exception.ValidationException;
import br.com.f1rst.produto.model.ProdutoModel;
import br.com.f1rst.produto.repository.ProdutoRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProdutoService {

    final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoModel salvar(ProdutoModel produtoModel) throws ValidationException {
        if (produtoModel.getNome() == null) {
            throw new ValidationException("Digite um nome.");
        }
        if (produtoModel.getNome().length() > 50){
            throw new ValidationException("Digite um nome com menos de 50 caracteres. ");
        }
        if (produtoModel.getQuantidade() == null){
            throw new ValidationException("Digite a quantidade do produto.");
        }

        return produtoRepository.save(produtoModel);
    }

    public void excluir(Long idProduto) {
        produtoRepository.deleteById(idProduto);
    }

    public ProdutoModel getById(long idProduto) {
        ProdutoModel produtoModel = produtoRepository.findById(idProduto).orElse(null);
        if (produtoModel == null) {
            throw new NotFoundException("ID nao encontrado");
        }
        return produtoModel;
    }

    public List<ProdutoModel> listarTodos() {

        return (List<ProdutoModel>) produtoRepository.findAll();
    }
}

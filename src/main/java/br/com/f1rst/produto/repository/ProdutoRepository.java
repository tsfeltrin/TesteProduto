package br.com.f1rst.produto.repository;

import br.com.f1rst.produto.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

}

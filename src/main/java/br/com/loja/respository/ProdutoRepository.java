package br.com.loja.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, String>
{

}

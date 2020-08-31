package br.com.loja.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja.model.Item;
import br.com.loja.model.Produto;

public interface ItemRepository extends JpaRepository<Item, String>
{

	Optional<Item> findByProduto(Produto produto);

}

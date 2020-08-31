package br.com.loja.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja.model.Pedido;
import br.com.loja.model.Produto;

public interface PedidoRepository extends JpaRepository<Pedido, String>
{
	Optional<Pedido> findByItensProduto(Optional<Produto> produto);
}

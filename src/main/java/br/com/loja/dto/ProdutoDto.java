package br.com.loja.dto;

import org.springframework.data.domain.Page;

import br.com.loja.model.Produto;
import br.com.loja.model.TipoProduto;
import lombok.Getter;

@Getter
public class ProdutoDto 
{
	private String id;
	
	private TipoProduto tipo;
	
	private String nome;
	
	public ProdutoDto(Produto produto)
	{
		this.id = produto.getId();
		this.tipo = produto.getTipo();
		this.nome = produto.getNome();
	}

	public static Page<ProdutoDto> converter(Page<Produto> produtos) 
	{
		return produtos.map(ProdutoDto::new);
	}
}

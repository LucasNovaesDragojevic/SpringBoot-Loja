package br.com.loja.dto;

import java.math.BigDecimal;

import br.com.loja.model.Produto;
import br.com.loja.model.TipoProduto;

import lombok.Getter;

@Getter
public class ProdutoDetalheDto 
{
	private String id;
	
	private Boolean ativo;
	
	private TipoProduto tipo;
	
	private String nome;
	
	private BigDecimal preco;
	
	public ProdutoDetalheDto(Produto produto)
	{
		this.id = produto.getId();
		this.ativo = produto.getAtivo();
		this.tipo = produto.getTipo();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}
}

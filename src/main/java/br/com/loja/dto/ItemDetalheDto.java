package br.com.loja.dto;

import br.com.loja.model.Item;
import br.com.loja.model.Produto;

import lombok.Getter;

@Getter
public class ItemDetalheDto 
{
	private String id;

	private Produto produto;
	
	private Integer quantidade;
	
	public ItemDetalheDto(Item item)
	{
		this.id = item.getId();
		this.produto = item.getProduto();
		this.quantidade = item.getQuantidade();
	}
}

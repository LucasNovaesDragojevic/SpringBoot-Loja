package br.com.loja.dto;

import org.springframework.data.domain.Page;

import br.com.loja.model.Item;

import lombok.Getter;

@Getter
public class ItemDto 
{
	private String id;

	private String produto;
	
	private Integer quantidade;
	
	public ItemDto(Item item)
	{
		this.id = item.getId();
		this.produto = item.getProduto().getId();
		this.quantidade = item.getQuantidade();
	}

	public static Page<ItemDto> converter(Page<Item> itens) 
	{
		return itens.map(ItemDto::new);
	}
}

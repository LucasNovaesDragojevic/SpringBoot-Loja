package br.com.loja.form;

import br.com.loja.model.Item;
import br.com.loja.model.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm 
{	
	private String id;
	
	private String produto;
	
	private Integer quantidade;
	
	public Item converter(Produto produto)
	{
		return new Item(produto, quantidade);
	}

	public Item update(Item item, Produto produto) 
	{
		item.setProduto(produto);
		item.setQuantidade(quantidade);
		return item;
	}
}

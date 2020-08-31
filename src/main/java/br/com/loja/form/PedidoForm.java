package br.com.loja.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.loja.model.Item;
import br.com.loja.model.Pedido;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PedidoForm 
{
	private String id;
	
	private Boolean ativo;
	
	private BigDecimal desconto = BigDecimal.ZERO;
	
	private List<String> itens = new ArrayList<String>();
	
	public Pedido converter(List<Item> itens)
	{
		return new Pedido(itens);
	}

	public Pedido update(Pedido pedido, List<Item> itens) 
	{
		pedido.setAtivo(ativo);
		pedido.setItens(itens);
		return pedido;
	}
}

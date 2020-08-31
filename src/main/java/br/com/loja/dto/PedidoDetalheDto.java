package br.com.loja.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.loja.model.Item;
import br.com.loja.model.Pedido;

import lombok.Getter;

@Getter
public class PedidoDetalheDto 
{
	private String id;
	
	private Boolean ativo;
	
	private BigDecimal valorTotal;
	
	private List<Item> itens = new ArrayList<Item>();
	
	public PedidoDetalheDto(Pedido pedido)
	{
		this.id = pedido.getId();
		this.ativo = pedido.getAtivo();
		this.itens = pedido.getItens();
		this.valorTotal = pedido.getValorTotal();
	}
}

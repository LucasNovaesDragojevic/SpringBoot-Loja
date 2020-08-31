package br.com.loja.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.loja.model.Pedido;

import lombok.Getter;

@Getter
public class PedidoDto 
{
	private String id;

	private Boolean ativo;
	
	private List<String> itens = new ArrayList<String>();
	
	public PedidoDto(Pedido pedido)
	{
		this.id = pedido.getId();
		this.ativo = pedido.getAtivo();
		pedido.getItens().forEach(item -> itens.add(item.getId()));
	}

	public static Page<PedidoDto> converter(Page<Pedido> pedidos) 
	{
		return pedidos.map(PedidoDto::new);
	}
}

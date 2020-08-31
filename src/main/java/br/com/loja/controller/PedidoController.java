package br.com.loja.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.loja.dto.PedidoDetalheDto;
import br.com.loja.dto.PedidoDto;
import br.com.loja.form.PedidoForm;
import br.com.loja.model.Item;
import br.com.loja.model.Pedido;
import br.com.loja.model.TipoProduto;
import br.com.loja.respository.ItemRepository;
import br.com.loja.respository.PedidoRepository;

@RestController
@RequestMapping("pedidos")
@Transactional
public class PedidoController 
{
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public Page<PedidoDto> list(Pageable pagination)
	{
		Page<Pedido> pedidos = pedidoRepository.findAll(pagination);
		
		return PedidoDto.converter(pedidos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PedidoDetalheDto> getById(@PathVariable String id)
	{
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isPresent())
			return ResponseEntity.ok().body(new PedidoDetalheDto(pedido.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<PedidoDto> add(@RequestBody PedidoForm pedidoForm, UriComponentsBuilder uriBuilder)
	{	
		
		List<Item> itens = itemRepository.findAllById(pedidoForm.getItens());
		
		if (itens.isEmpty())
			return ResponseEntity.badRequest().build();
			
		Pedido pedido = pedidoForm.converter(itens);
		
		pedido.setValorTotal(calcularValorTotal(pedidoForm.getDesconto(), pedido));
		
		pedidoRepository.save(pedido);
		URI uri = uriBuilder.path("pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<PedidoDto> update(@PathVariable String id, @RequestBody PedidoForm pedidoForm)
	{
		List<Item> itens = itemRepository.findAllById(pedidoForm.getItens());
		
		if (itens.isEmpty())
			return ResponseEntity.badRequest().build();
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		if (optional.isPresent())
		{
			Pedido pedido = pedidoForm.update(optional.get(), itens);
			
			if (pedidoForm.getDesconto() != BigDecimal.ZERO && pedido.getAtivo() == false)
			{
				pedido.setValorTotal((calcularValorTotal(BigDecimal.ZERO, pedido)));
				return ResponseEntity.badRequest().build();
			}
				
			pedido.setValorTotal((calcularValorTotal(pedidoForm.getDesconto(), pedido)));
			return ResponseEntity.ok().body(new PedidoDto(pedido));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id)
	{
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isPresent())
		{
			pedidoRepository.delete(pedido.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	private BigDecimal calcularValorTotal(BigDecimal desconto, Pedido pedido) 
	{	
		BigDecimal valorTotalServico = BigDecimal.ZERO;
		BigDecimal valorTotalProduto = BigDecimal.ZERO;
		BigDecimal valorTotalDesconto = BigDecimal.ZERO;
		
		for (Item item : pedido.getItens()) 
		{
			if (item.getProduto().getTipo() == TipoProduto.SERVICO)
			{
				valorTotalServico = valorTotalServico.add(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
			}
			
			if (item.getProduto().getTipo() == TipoProduto.PRODUTO)
			{
				valorTotalProduto = valorTotalProduto.add(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
			}
		}
		
		if (desconto != BigDecimal.ZERO)
		{
			valorTotalDesconto = valorTotalProduto.multiply(desconto);
			valorTotalProduto = valorTotalProduto.subtract(valorTotalDesconto);
		}
		
		return valorTotalServico.add(valorTotalProduto);
	}
}

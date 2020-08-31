package br.com.loja.controller;

import java.net.URI;
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

import br.com.loja.dto.ProdutoDetalheDto;
import br.com.loja.dto.ProdutoDto;
import br.com.loja.form.ProdutoForm;
import br.com.loja.model.Pedido;
import br.com.loja.model.Produto;
import br.com.loja.respository.PedidoRepository;
import br.com.loja.respository.ProdutoRepository;

@RestController
@RequestMapping("produtos")
@Transactional
public class ProdutoController 
{
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired PedidoRepository pedidoRepository;
	
	@GetMapping
	public Page<ProdutoDto> list(Pageable pagination)
	{
		Page<Produto> produtos = produtoRepository.findAll(pagination);
		
		return ProdutoDto.converter(produtos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProdutoDetalheDto> getById(@PathVariable String id)
	{
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent())
			return ResponseEntity.ok().body(new ProdutoDetalheDto(produto.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDto> add(@RequestBody ProdutoForm produtoForm, UriComponentsBuilder uriBuilder)
	{	
		Produto produto = produtoForm.converter();
		produtoRepository.save(produto);
		URI uri = uriBuilder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProdutoDto(produto));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ProdutoDto> update(@PathVariable String id, @RequestBody ProdutoForm produtoForm)
	{
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent())
		{
			Produto produto = produtoForm.update(optional.get());
			return ResponseEntity.ok().body(new ProdutoDto(produto));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id)
	{
		Optional<Produto> produto = produtoRepository.findById(id);
		
		Optional<Pedido> pedido = pedidoRepository.findByItensProduto(produto);
		
		if (pedido.isPresent())
		{
			return ResponseEntity.badRequest().build();
		}

		if (produto.isPresent())
		{
			produtoRepository.delete(produto.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}

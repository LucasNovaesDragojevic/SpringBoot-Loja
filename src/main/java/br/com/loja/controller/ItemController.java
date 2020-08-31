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

import br.com.loja.dto.ItemDetalheDto;
import br.com.loja.dto.ItemDto;
import br.com.loja.form.ItemForm;
import br.com.loja.model.Item;
import br.com.loja.model.Produto;
import br.com.loja.respository.ItemRepository;
import br.com.loja.respository.ProdutoRepository;

@RestController
@RequestMapping("itens")
@Transactional
public class ItemController 
{
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public Page<ItemDto> list(Pageable pagination)
	{
		Page<Item> items = itemRepository.findAll(pagination);
		
		return ItemDto.converter(items);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ItemDetalheDto> getById(@PathVariable String id)
	{
		Optional<Item> item = itemRepository.findById(id);
		if (item.isPresent())
			return ResponseEntity.ok().body(new ItemDetalheDto(item.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<ItemDto> add(@RequestBody ItemForm itemForm, UriComponentsBuilder uriBuilder)
	{	
		Optional<Produto> produto = produtoRepository.findById(itemForm.getProduto());
		
		if (!produto.isPresent())
			return ResponseEntity.badRequest().build();
			
		if (produto.get().getAtivo() == false)
			return ResponseEntity.badRequest().build();
			
		Item item = itemForm.converter(produto.get());
		itemRepository.save(item);
		URI uri = uriBuilder.path("items/{id}").buildAndExpand(item.getId()).toUri();
		return ResponseEntity.created(uri).body(new ItemDto(item));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ItemDto> update(@PathVariable String id, @RequestBody ItemForm itemForm)
	{
		Optional<Item> optionalItem = itemRepository.findById(id);
		Optional<Produto> optionalProduto = produtoRepository.findById(itemForm.getProduto());
		
		if (optionalItem.isPresent() && optionalProduto.isPresent())
		{		
			if (optionalProduto.get().getAtivo() == false)
				return ResponseEntity.badRequest().build();
			
			Item item = itemForm.update(optionalItem.get(), optionalProduto.get());
			return ResponseEntity.ok().body(new ItemDto(item));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id)
	{
		Optional<Item> item = itemRepository.findById(id);
		if (item.isPresent())
		{
			itemRepository.delete(item.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}

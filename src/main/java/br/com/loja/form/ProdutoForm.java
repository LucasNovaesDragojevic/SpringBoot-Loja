package br.com.loja.form;

import java.math.BigDecimal;

import br.com.loja.model.Produto;
import br.com.loja.model.TipoProduto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoForm 
{	
	private Boolean ativo;
	
	private TipoProduto tipo;
	
	private String nome;
	
	private BigDecimal preco;
	
	public Produto converter()
	{
		return new Produto(tipo, nome, preco);
	}

	public Produto update(Produto produto) 
	{
		produto.setAtivo(ativo);
		produto.setTipo(tipo);
		produto.setNome(nome);
		produto.setPreco(preco);
		return produto;
	}
}

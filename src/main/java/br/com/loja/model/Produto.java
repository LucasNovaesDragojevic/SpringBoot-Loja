package br.com.loja.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto 
{
	@Id
	@EqualsAndHashCode.Include
	private String id = UUID.randomUUID().toString();

	private Boolean ativo = true;
	
	@Enumerated(EnumType.STRING)
	private TipoProduto tipo;
	
	private String nome;
	
	private BigDecimal preco;
	
	public Produto(TipoProduto tipo, String nome, BigDecimal preco) 
	{
		this.tipo = tipo;
		this.nome = nome;
		this.preco = preco;
	}
	
}

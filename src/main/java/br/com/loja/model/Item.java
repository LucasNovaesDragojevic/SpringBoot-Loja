package br.com.loja.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item 
{
	@Id
	@EqualsAndHashCode.Include
	private String id = UUID.randomUUID().toString();
	
	@ManyToOne
	private Produto produto;
	
	private Integer quantidade;
	
	public Item(Produto produto, Integer quantidade)
	{
		this.produto = produto;
		this.quantidade = quantidade;
	}
}

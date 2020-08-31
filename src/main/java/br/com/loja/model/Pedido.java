package br.com.loja.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido 
{
	@Id
	@EqualsAndHashCode.Include
	private String id = UUID.randomUUID().toString();
	
	private Boolean ativo = true;

	private BigDecimal valorTotal;
	
	@OneToMany
	private List<Item> itens = new ArrayList<Item>();
	
	public Pedido(List<Item> itens) 
	{
		this.itens = itens;
	}
}
package br.com.loja;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import br.com.loja.controller.ItemController;
import br.com.loja.controller.PedidoController;
import br.com.loja.controller.ProdutoController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LojaApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProdutoController produtoController;
	
	@Autowired
	private ItemController itemController;
	
	@Autowired
	private PedidoController pedidoController;
	
	@LocalServerPort
    private int port;
	
	@Test
	void contextLoads() {
		assertThat(produtoController).isNotNull();
		assertThat(itemController).isNotNull();
		assertThat(pedidoController).isNotNull();
	}
	
	@Test
	void testaGetsEntitades() throws Exception
	{
		this.mockMvc.perform(get("/produtos"))
					.andDo(print())
					.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/itens"))
					.andDo(print())
					.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/pedidos"))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void testaRetornoDeRecursoInexistente() throws Exception 
	{
		this.mockMvc.perform(get("/produtos/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		this.mockMvc.perform(get("/itens/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		this.mockMvc.perform(get("/pedidos/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
	}
	
	@Test
	public void testaRetornoDePostInvalido() throws Exception 
	{
		this.mockMvc.perform(post("/pedidos"))
					.andDo(print())
					.andExpect(status().isBadRequest());
		
		this.mockMvc.perform(post("/pedidos"))
					.andDo(print())
					.andExpect(status().isBadRequest());

		this.mockMvc.perform(post("/pedidos"))
					.andDo(print())
					.andExpect(status().isBadRequest());

	}
	
	@Test
	public void testaAtualizacaoDeRecursoInvalida() throws Exception 
	{
		this.mockMvc.perform(put("/produtos/1"))
					.andDo(print())
					.andExpect(status().isBadRequest());
		
		this.mockMvc.perform(put("/itens/1"))
					.andDo(print())
					.andExpect(status().isBadRequest());
					
		this.mockMvc.perform(put("/pedidos/1"))
					.andDo(print())
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testaRemocaoDeRecursosInvalidos() throws Exception 
	{
		this.mockMvc.perform(delete("/produtos/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		this.mockMvc.perform(delete("/itens/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		this.mockMvc.perform(delete("/pedidos/1"))
					.andDo(print())
					.andExpect(status().isNotFound());
	}
}

package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiUsuariosApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	
	private static String email;
	private static String senha;
	
	@Test
	@Order(1)
	void criarUsuarioComSucesso() throws Exception {
	
		var faker = new Faker();
		
		var request = new CriarUsuarioRequestDto();
		request.setNome(faker.name().fullName());
		request.setEmail(faker.internet().emailAddress());
		request.setSenha("@Admin123");
		
		mockMvc.perform(post("/api/usuario/criar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
	
		email = request.getEmail();
		senha = request.getSenha();
		
	}

	@Test
	@Order(2)
	void autenticarUsuarioComSucesso() throws Exception {
		
		var request = new AutenticarUsuarioRequestDto();
		request.setEmail(email);
		request.setSenha(senha);
		
		mockMvc.perform(post("/api/usuario/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
		
	}

	@Test
	@Order(3)
	void erroAoCriarUsuarioJaExistente() throws Exception {
	
		
		var request = new CriarUsuarioRequestDto();
		request.setNome("Usuário teste");
		request.setEmail(email);
		request.setSenha("@Admin123");
		
		var result = mockMvc.perform(post("/api/usuario/criar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andReturn();
	
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(content.contains("O email informado já está cadastrado, tente outro."));
		
		
	}

	@Test
	@Order(4)
	void acessoNegadoParaUsuarioInvalido() throws Exception {
		
		var request = new AutenticarUsuarioRequestDto();
		request.setEmail(email);
		request.setSenha("@Test2025");
		
		var result = mockMvc.perform(post("/api/usuario/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andReturn();
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(content.contains("Acesso negado. Usuário não encontrado."));
		
	}

}

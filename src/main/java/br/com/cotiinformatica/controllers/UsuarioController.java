package br.com.cotiinformatica.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Operation(summary = "Serviço da API para criar novo usuário.")
	@PostMapping("criar")
	public void criar() {
		// TODO
	}
	
	@Operation(summary = "Serviço da API para autenticar um usuário.")
	@PostMapping("autenticar")
	public void autenticar() {
		//TODO
	}
}


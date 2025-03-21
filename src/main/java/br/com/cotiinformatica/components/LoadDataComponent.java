package br.com.cotiinformatica.components;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.cotiinformatica.entities.Perfil;
import br.com.cotiinformatica.repositories.PerfilRepository;

@Component
public class LoadDataComponent implements ApplicationRunner {

	
	@Autowired PerfilRepository perfilRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		var perfilAdministrador = new Perfil();
		perfilAdministrador.setId(UUID.fromString("cfd306c0-c516-4176-a215-bb7a49e54c6f"));
		perfilAdministrador.setNome("Administrador");
		
		var perfilOperador = new Perfil();
		perfilOperador.setId(UUID.fromString("7f55d810-f21a-4052-9d39-6ef61cbe85b2"));
		perfilOperador.setNome("Operador");
		
		//gravando no banco de dados
		perfilRepository.save(perfilAdministrador);
		perfilRepository.save(perfilOperador);
	}
}

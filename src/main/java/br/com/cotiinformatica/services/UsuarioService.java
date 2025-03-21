package br.com.cotiinformatica.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.components.SHA256Component;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponseDto;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.PerfilRepository;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired UsuarioRepository usuarioRepository;
	@Autowired PerfilRepository perfilRepository;
	@Autowired SHA256Component sha256Component;
	@Autowired JwtTokenComponent jwtTokenComponent;
	
	
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) {
		
	if (usuarioRepository.findByEmail(request.getEmail()) !=null) {
		throw new IllegalArgumentException("O email informado já está cadastrado, tente outro.");
	}
	
	var usuario = new Usuario();
	
	usuario.setId(UUID.randomUUID());
	usuario.setNome(request.getNome());
	usuario.setEmail(request.getEmail());
	usuario.setSenha(sha256Component.encrypt(request.getSenha()));
	usuario.setPerfil(perfilRepository.findByNome("Operador"));
	
	usuarioRepository.save(usuario);
	
	var response = new CriarUsuarioResponseDto();
	
	response.setId(usuario.getId());
	response.setNome(usuario.getNome());
	response.setEmail(usuario.getEmail());
	response.setDataCriacao(Instant.now());
	response.setPerfil(usuario.getPerfil().getNome());
	
		return response;
	}

	public AutenticarUsuarioResponseDto autenticarUsuario(AutenticarUsuarioRequestDto request) {

		var usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), sha256Component.encrypt(request.getSenha()));
		
		if(usuario == null) {
			throw new IllegalArgumentException("Acesso negado. Usuário não encontrado");
			
		}
		
		var response = new AutenticarUsuarioResponseDto();
		
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setPerfil(usuario.getPerfil().getNome());
		response.setToken(jwtTokenComponent.getToken(usuario));
		
		return response;
	}
}
